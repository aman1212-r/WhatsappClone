package com.example.whatsappclone.homescreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.whatsappclone.viewmodels.BaseViewModel
import  androidx.compose.runtime.getValue
import  androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.R
import com.example.whatsappclone.bottomnavigation.BottomNavigation
import com.example.whatsappclone.chat_box.ChatDesignModel
import com.example.whatsappclone.navigation.Routes
import com.google.firebase.auth.FirebaseAuth


@Composable

fun Homescreen(navHostController: NavHostController, homeBaseViewModel: BaseViewModel) {

    var showPopup by remember {
        mutableStateOf(false)

    }

    val chatData by homeBaseViewModel.chatList.collectAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (userId != null) {

        LaunchedEffect(userId) {
            homeBaseViewModel.getChatForUser(userId) { chats ->
            }
        }
    }

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorResource(id = R.color.light_green),
                contentColor = Color.White,
                modifier = Modifier
                    .size(65.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_chat_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp),
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            BottomNavigation()
        }) {


        Column(
            modifier = Modifier
                .padding(it)
                .background(color = Color.White)
        ) {

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {


                var isSearching by remember { mutableStateOf(false) }

                var searchText by remember { mutableStateOf("") }

                var showMenu by remember { mutableStateOf(false) }

                if (isSearching) {

                    TextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        placeholder = {
                            Text(
                                text = "Search",
                                color = Color.Gray
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                            .fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.colors(

                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                } else {

                    Text(
                        text = "WhatsApp",
                        fontSize = 28.sp,
                        color = colorResource(R.color.light_green),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp),
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.camera),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }

                        if (isSearching) {
                            IconButton(onClick = {
                                isSearching = false
                                searchText = ""

                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.cross),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }

                        } else {
                            IconButton(onClick = {
                                isSearching = true
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                        IconButton(onClick = {
                            showMenu = !showMenu
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            DropdownMenu(
                                expanded = showMenu, onDismissRequest = {
                                    showMenu = false
                                }, modifier = Modifier
                                    .background(color = Color.White)
                            ) {

                                DropdownMenuItem(
                                    text = { Text(text = "New Group") },
                                    onClick = { showMenu = false })


                                DropdownMenuItem(
                                    text = { Text(text = "New Broadcast") },
                                    onClick = { showMenu = false })


                                DropdownMenuItem(
                                    text = { Text(text = "Linked Device") },
                                    onClick = { showMenu = false })


                                DropdownMenuItem(
                                    text = { Text(text = "Starred Messages") },
                                    onClick = { showMenu = false })


                                DropdownMenuItem(
                                    text = { Text(text = "Setting") },
                                    onClick = {
                                        showMenu = false
                                        navHostController.navigate(Routes.SettingScreen)
                                    })
                            }
                        }
                    }


                }

            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            LazyColumn() {

                }
            }

        }

    }
