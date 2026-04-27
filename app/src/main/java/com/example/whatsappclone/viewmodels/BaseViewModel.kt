package com.example.whatsappclone.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.chat_box.ChatDesignModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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

                        val user = snapshot.children.first().getValue(ChatDesignModel::class.java)

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

        chatref.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val chatList = mutableListOf<ChatDesignModel>()

                    for (childsnapshot in snapshot.children) {

                        val chat = childsnapshot.getValue(ChatDesignModel::class.java)

                        if (chat != null) {
                            chatList.add(chat)
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

            val chatRef = FirebaseDatabase.getInstance().getReference("chats")

            chatRef.orderByChild("userId").equalTo(currentUserId)
                .addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val chatList = mutableListOf<ChatDesignModel>()
                        for (childSnapshot in snapshot.children) {


                            val chat = childSnapshot.getValue(ChatDesignModel::class.java)

                            if (chat != null) {

                                chatList.add(chat)

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

            val newChatRef = FirebaseDatabase.getInstance().getReference("chats")
                .push()
            val chatWithUser = newChat.copy(currentUserId)

            newChatRef.setValue(chatWithUser).addOnSuccessListener {
                Log.d("BaseViewModel", "Chat added successfully to Firebase")
            }.addOnFailureListener {

                    exception ->
                Log.e("BaseViewModel", "Failed to add chat: ${exception.message}")

            }

        }else{
            Log.e("BaseViewModel" , "no User is authenticated")
        }


    }

}