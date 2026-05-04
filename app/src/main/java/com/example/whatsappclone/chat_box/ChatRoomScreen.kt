package com.example.whatsappclone.chat_box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.models.Message
import com.example.whatsappclone.viewmodels.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatRoomScreen(
    navHostController: NavHostController,
    name: String,
    phoneNumber: String,
    image: Int?,
    baseViewModel: BaseViewModel = viewModel()
) {
    val currentUserPhoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber.orEmpty()
    val messages = remember { mutableStateListOf<Message>() }
    val listState = rememberLazyListState()
    var draftMessage by remember { mutableStateOf("") }

    LaunchedEffect(currentUserPhoneNumber, phoneNumber) {
        if (currentUserPhoneNumber.isNotEmpty()) {
            baseViewModel.getMessage(currentUserPhoneNumber, phoneNumber) { message ->
                val alreadyExists = messages.any {
                    it.timestamp == message.timestamp &&
                        it.message == message.message &&
                        it.senderPhoneNumber == message.senderPhoneNumber
                }
                if (!alreadyExists) {
                    messages.add(message)
                }
            }
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            ChatRoomTopBar(
                name = name,
                phoneNumber = phoneNumber,
                image = image,
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF7F7F7))
                .navigationBarsPadding()
                .imePadding()
        ) {
            if (messages.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ChatEmptyState()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(messages, key = { "${it.timestamp}-${it.senderPhoneNumber}-${it.message}" }) { message ->
                        val isMine = message.senderPhoneNumber == currentUserPhoneNumber
                        ChatMessageBubble(message = message, isMine = isMine)
                    }
                }
            }

            ChatMessageComposer(
                message = draftMessage,
                onMessageChange = { draftMessage = it },
                onSendClick = {
                    val trimmed = draftMessage.trim()
                    if (trimmed.isNotEmpty() && currentUserPhoneNumber.isNotEmpty()) {
                        baseViewModel.sendMessage(
                            senderPhoneNumber = currentUserPhoneNumber,
                            receiverPhoneNumber = phoneNumber,
                            messageText = trimmed
                        )
                        draftMessage = ""
                    }
                },
                isSendEnabled = draftMessage.trim().isNotEmpty() && currentUserPhoneNumber.isNotEmpty()
            )
        }
    }
}
