package com.example.whatsappclone.chat_box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.R
import com.example.whatsappclone.models.Message

@Composable
fun ChatRoomTopBar(
    name: String,
    phoneNumber: String,
    image: Int?,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(26.dp),
                    tint = Color(0xFF1D1D1F)
                )
            }

            ChatContactAvatar(image = image)

            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color(0xFF1D1D1F)
                )
                Text(
                    text = phoneNumber,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
        HorizontalDivider(color = Color(0xFFE7E7E7))
    }
}

@Composable
fun ChatContactAvatar(image: Int?) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFFF0F0F0)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(image ?: R.drawable.ic_profile_placeholder),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun ChatMessageBubble(
    message: Message,
    isMine: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(
                    if (isMine) colorResource(R.color.light_green) else Color.White
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = message.message,
                color = if (isMine) Color.White else Color(0xFF1D1D1F)
            )
        }
    }
}

@Composable
fun ChatEmptyState() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Start the conversation",
            color = Color.Gray
        )
    }
}

@Composable
fun ChatMessageComposer(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isSendEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = message,
            onValueChange = onMessageChange,
            placeholder = {
                Text("Type a message")
            },
            modifier = Modifier.weight(1f),
            singleLine = false,
            maxLines = 4,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFD9D9D9),
                unfocusedIndicatorColor = Color(0xFFD9D9D9)
            )
        )

        FilledIconButton(
            onClick = onSendClick,
            enabled = isSendEnabled,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(48.dp),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Send,
                contentDescription = "Send",
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
