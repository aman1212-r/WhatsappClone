package com.example.whatsappclone.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.animation.core.snap
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.R
import com.example.whatsappclone.chat_box.ChatDesignModel
import com.example.whatsappclone.models.Message
import com.example.whatsappclone.models.PhoneAuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okio.IOException
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.io.encoding.ExperimentalEncodingApi

class BaseViewModel : ViewModel() {

    // SearchUserByPhoneNumber Function

    fun searchUserByPhoneNumber(
        phoneNumber: String, callback: (ChatDesignModel?) -> Unit
    ) {

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {
            Log.e("BaseViewModel", "Current user is null")
            callback(null)
            return
        }

        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        val userSnapshot = snapshot.children.firstOrNull()
                        val user = userSnapshot?.getValue(PhoneAuthUser::class.java)?.let {
                            ChatDesignModel(
                                name = it.name,
                                phoneNumber = it.phoneNumber,
                                image = R.drawable.ic_profile_placeholder,
                                userId = it.userId,
                                profileImage = it.profileImage
                            )
                        }

                        if (user?.userId == currentUser.uid) {
                            callback(null)
                            return
                        }

                        callback(user)

                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "BaseViewModel", "Error Fetching User: ${error.details}"
                    )
                    callback(null)
                }
            })
    }

    // GetChatForUser Function

    fun getChatForUser(
        userId: String, callback: (List<ChatDesignModel>) -> Unit
    ) {

        val chatref = FirebaseDatabase.getInstance().getReference("users/$userId/chats")

        chatref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val chatList = mutableListOf<ChatDesignModel>()

                for (childsnapshot in snapshot.children) {

                    val chat = childsnapshot.getValue(ChatDesignModel::class.java)

                    if (chat != null) {
                        chatList.add(chat.copy(image = chat.image ?: R.drawable.ic_profile_placeholder))
                    }
                }

                callback(chatList)
                _chatList.value = chatList
            }

            override fun onCancelled(error: DatabaseError) {

                Log.e(
                    "BaseViewModel", "Error Fetching Chats: ${error.message}"
                )

                callback(emptyList())
            }
        })
    }

    private val _chatList = MutableStateFlow<List<ChatDesignModel>>(emptyList())

    val chatList = _chatList.asStateFlow()

    init {
        loadChatData()

    }


    //loadChatData function


    private fun loadChatData() {

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {

            val chatRef = FirebaseDatabase.getInstance().getReference("users/$currentUserId/chats")

            chatRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val chatList = mutableListOf<ChatDesignModel>()
                    for (childSnapshot in snapshot.children) {


                        val chat = childSnapshot.getValue(ChatDesignModel::class.java)

                        if (chat != null) {

                            chatList.add(
                                chat.copy(image = chat.image ?: R.drawable.ic_profile_placeholder)
                            )

                        }
                    }

                    _chatList.value = chatList
                }

                override fun onCancelled(error: DatabaseError) {

                    Log.e(
                        "BaseViewModel", "Error Fetching Chats: ${error.message}"
                    )

                }

            })
        }
    }

    // AddChat Function


    fun addChat(newChat: ChatDesignModel) {

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {

            val userRef = FirebaseDatabase.getInstance().getReference("users")
            val chatKey = newChat.userId ?: newChat.phoneNumber ?: return
            val currentUserChatRef = userRef.child(currentUserId).child("chats").child(chatKey)
            val chatWithUser = newChat.copy(
                image = newChat.image ?: R.drawable.ic_profile_placeholder
            )

            currentUserChatRef.setValue(chatWithUser).addOnSuccessListener {
                _chatList.value = (_chatList.value + chatWithUser).distinctBy {
                    it.userId ?: it.phoneNumber ?: it.name
                }
                Log.d("BaseViewModel", "Chat added successfully to Firebase")
            }.addOnFailureListener {

                    exception ->
                Log.e("BaseViewModel", "Failed to add chat: ${exception.message}")

            }

            userRef.child(currentUserId).get().addOnSuccessListener { snapshot ->
                val currentUserProfile = snapshot.getValue(PhoneAuthUser::class.java)
                if (currentUserProfile != null && !newChat.userId.isNullOrEmpty()) {
                    val reverseChat = ChatDesignModel(
                        name = currentUserProfile.name,
                        phoneNumber = currentUserProfile.phoneNumber,
                        image = R.drawable.ic_profile_placeholder,
                        userId = currentUserId,
                        profileImage = currentUserProfile.profileImage
                    )
                    userRef.child(newChat.userId).child("chats").child(currentUserId)
                        .setValue(reverseChat)
                }
            }

        } else {
            Log.e("BaseViewModel", "no User is authenticated")
        }


    }


    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun sendMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        messageText: String
    ) {

        val messageId = databaseReference.push().key ?: return

        val message = Message(
            senderPhoneNumber = senderPhoneNumber,
            message = messageText,
            timestamp = System.currentTimeMillis()
        )

        //database for sender

        databaseReference.child("messages").child(senderPhoneNumber).child(receiverPhoneNumber)
            .child(messageId).setValue(message)


        //database for receiver

        databaseReference.child("messages").child(receiverPhoneNumber).child(senderPhoneNumber)
            .child(messageId).setValue(message)
    }


    // get message function

    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onNewMessage: (Message) -> Unit
    ) {

        val messageRef =
            databaseReference.child("messages").child(senderPhoneNumber).child(receiverPhoneNumber)

        messageRef.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    onNewMessage(message)
                }


            }

            override fun onChildChanged(
                p0: DataSnapshot, p1: String?
            ) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildMoved(
                p0: DataSnapshot, p1: String?
            ) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })

    }


    // fetch last message for chat function

    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched: (String, String) -> Unit

    ) {

        val chatRef = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        chatRef.orderByChild("timestamp")
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        val lastMessage =
                            snapshot.children.firstOrNull()?.child("message")?.value as? String

                        val timestamp =
                            snapshot.children.firstOrNull()?.child("timestamp")?.getValue(Long::class.java)

                        onLastMessageFetched(
                            lastMessage ?: "No message",
                            timestamp?.let { formatTimestamp(it) } ?: "--:--"
                        )

                    } else {
                        onLastMessageFetched("No message", "--:--")
                    }

                }

                override fun onCancelled(p0: DatabaseError) {

                    onLastMessageFetched("No message", "--:--")
                }


            })
    }


    // Load Chat List function

    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListLoaded: (List<ChatDesignModel>) -> Unit

    ) {

        val chatList = mutableListOf<ChatDesignModel>()
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("chats")
            .child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    snapshot.children.forEach { child ->

                        val phoneNumber = child.key ?: return@forEach
                        val name = child.child("name").value as? String ?: "Unknown"
                        val image = child.child("profileImage").value as? String
                        val profileImageBitmap = image?.let {
                            decodeBase64toBitmap(it)
                        }
                        fetchLastMessageForChat(
                            currentUserPhoneNumber,
                            phoneNumber
                        ) { lastMessage, time ->

                            chatList.add(
                                ChatDesignModel(
                                    name = name,
                                    message = lastMessage,
                                    time = time,
                                    profileImage = if (profileImageBitmap != null) image else null
                                )
                            )

                            if (chatList.size == snapshot.childrenCount.toInt()) {
                                onChatListLoaded(chatList)
                            }
                        }


                    }
                } else {
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(p0: DatabaseError) {

                onChatListLoaded(emptyList())
            }

        })

    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64toBitmap(base64Image: String): Bitmap? {

        return try {

            val decodedBytes = Base64.decode(base64Image, android.util.Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IOException) {
            null
        }

    }

    @OptIn(ExperimentalEncodingApi::class)
    fun base64ToBitmap(base64String: String): Bitmap? {

        return try {

            val decodedBytes = Base64.decode(base64String, android.util.Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(decodedBytes)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            null
        }

    }

    private fun formatTimestamp(timestamp: Long): String {
        val formatter = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
        return formatter.format(java.util.Date(timestamp))
    }

}
