package com.example.whatsappclone.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.bottomnavigation.BottomNavigation
import com.example.whatsappclone.chat_box.ChatDesignModel
import com.example.whatsappclone.navigation.Routes
import com.example.whatsappclone.viewmodels.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Homescreen(
    navHostController: NavHostController,
    homeBaseViewModel: BaseViewModel = viewModel()
) {

    val chatData by homeBaseViewModel.chatList.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var showAddChatDialog by remember { mutableStateOf(false) }
    var phoneQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf<ChatDesignModel?>(null) }
    var searchError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        if (userId != null) {
            homeBaseViewModel.getChatForUser(userId) { }
        }
    }

    val filteredChatData = remember(chatData, searchText) {
        val query = searchText.trim()
        if (query.isEmpty()) {
            chatData
        } else {
            chatData.filter { chat ->
                val nameMatches = chat.name?.contains(query, ignoreCase = true) == true
                val phoneMatches = chat.phoneNumber?.contains(query, ignoreCase = true) == true
                nameMatches || phoneMatches
            }
        }
    }

    if (showAddChatDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddChatDialog = false
                phoneQuery = ""
                searchResult = null
                searchError = null
            },
            title = {
                Text(text = "New chat")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextField(
                        value = phoneQuery,
                        onValueChange = {
                            phoneQuery = it
                            searchError = null
                        },
                        label = {
                            Text("Phone number")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    searchResult?.let { user ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    homeBaseViewModel.addChat(user)
                                    showAddChatDialog = false
                                    phoneQuery = ""
                                    searchResult = null
                                    searchError = null
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = user.name ?: "Unknown user",
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = user.phoneNumber ?: "",
                                color = Color.Gray
                            )
                            Text(
                                text = "Tap to add chat",
                                color = colorResource(R.color.light_green),
                                fontSize = 12.sp
                            )
                        }
                    }

                    searchError?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val query = phoneQuery.trim()
                        if (query.isEmpty()) {
                            searchError = "Enter a phone number"
                        } else {
                            homeBaseViewModel.searchUserByPhoneNumber(query) { user ->
                                searchResult = user
                                searchError = if (user == null) {
                                    "No user found for this phone number"
                                } else {
                                    null
                                }
                            }
                        }
                    }
                ) {
                    Text("Search")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddChatDialog = false
                        phoneQuery = ""
                        searchResult = null
                        searchError = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddChatDialog = true
                },
                containerColor = colorResource(id = R.color.light_green),
                contentColor = Color.White,
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                navHostController = navHostController,
                currentRoute = Routes.HomeScreen
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .statusBarsPadding()
                .background(color = Color.White)
        ) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSearching) {
                    TextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        placeholder = {
                            Text(
                                text = "Search by name or number",
                                color = Color.Gray
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                            .fillMaxWidth(0.78f),
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
                }

                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            if (isSearching) {
                                isSearching = false
                                searchText = ""
                            } else {
                                isSearching = true
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isSearching) R.drawable.cross else R.drawable.search
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Box {
                        IconButton(
                            onClick = {
                                showMenu = !showMenu
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = {
                                showMenu = false
                            },
                            modifier = Modifier.background(color = Color.White)
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "New Group") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "New Broadcast") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Linked Device") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Starred Messages") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Settings") },
                                onClick = {
                                    showMenu = false
                                    navHostController.navigate(Routes.SettingScreen)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (filteredChatData.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchText.isBlank()) {
                            "No chats yet. Start a conversation."
                        } else {
                            "No chats found"
                        },
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = filteredChatData,
                        key = { chat -> chat.userId ?: chat.phoneNumber ?: chat.name ?: "" }
                    ) { chat ->
                        ChatDesign(chatDesignModel = chat)
                    }
                }
            }
        }
    }
}
