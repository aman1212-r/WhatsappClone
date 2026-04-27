package com.example.whatsappclone.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.chat_box.ChatDesignModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BaseViewModel : ViewModel() {


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
                    Log.e("BaseViewModel", "Error Fetching User: ${error.details} ")

                    callback(null)
                }

            })
    }


    fun getChatForUser(userId: String, callback: (List<ChatDesignModel>) -> Unit) {

        val chatref = FirebaseDatabase.getInstance().getReference("users/$userId/chats")
        chatref.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val chatList = mutableListOf<ChatDesignModel>()

                    for (childsnapshot in snapshot.children) {

                        val chat = childsnapshot.getValue(ChatDesignModel::class.java)

                        if (chat != null)
                            chatList.add(chat)

                    }

                    callback(chatList)
                }

                override fun onCancelled(error: DatabaseError) {

                    Log.e("BaseViewModel", "Error Fetching Chats: ${error.message}")
                    callback(emptyList())
                }


            })


    }
}
