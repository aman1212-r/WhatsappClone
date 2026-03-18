package com.example.whatsappclone.homescreen

import androidx.compose.foundation.layout.Box
import com.example.whatsappclone.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.BottomNavigation.BottomNavigation
import com.example.whatsappclone.chat_box.ChatDesignModel

@Composable
@Preview(showSystemUi = true)
fun Homescreen() {

    val chatData = listOf(
        ChatDesignModel(
            R.drawable.profile_picture,
            name = "Salman khan",
            time = "10:00Am",
            message = "hi",
        ),
        ChatDesignModel(
            R.drawable.rashmika,
            name = "Rashmi",
            time = "9:00",
            message = "hello",
        ),
        ChatDesignModel(
            R.drawable.rajkummar_rao,
            name = "Rajkumar rao",
            time = "11:00",
            message = "What are you doing?",
        ),
        ChatDesignModel(
            R.drawable.tripti_dimri,
            name = "Tripti",
            time = "12:00",
            message = "hey yoo .",
        )
    )


    // floating button
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorResource(id = R.color.light_green),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {


                Icon(
                    painter = painterResource(id = R.drawable.add_chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                )
            }
        },
        bottomBar = {
            BottomNavigation()
        }
    )
    {
        Column(modifier = Modifier.padding(it)) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth()) {


                Text(
                    text = "Whatsapp",
                    fontSize = 20.sp,
                    color = colorResource(R.color.light_green),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    fontWeight = FontWeight.Bold
                )

                // Camera icons

                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // search icon

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.more),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // horizontal divider
            //lazy column gives the vertical scroll features

            HorizontalDivider()
            HorizontalDivider()

            LazyColumn() {
                items(chatData) {
                    ChatDesign(chatDesignModel = it)
                }
            }
        }
    }
}