package com.example.whatsappclone.callscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // ✅ IMPORTANT IMPORT
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.R
import com.example.whatsappclone.bottomnavigation.BottomNavigation

@Preview(showSystemUi = true)
@Composable
fun CallScreen() {

    val sampleCall = listOf(
        Call(
            image = R.drawable.bhuvan_bam,
            name = "Bhumi Bam",
            time = "Yesterday, 8:30 PM",
            isMissed = true
        ), Call(
            image = R.drawable.boy1,
            name = "Sharuk khan",
            time = "Today, 10:00 AM",
            isMissed = false
        ), Call(
            image = R.drawable.akshay_kumar, name = "Akshay", time = "Nov, 8:35 PM", isMissed = true
        ), Call(
            image = R.drawable.sharadha_kapoorl,
            name = "Shradha",
            time = "Today, 6:30 PM",
            isMissed = true
        ), Call(
            image = R.drawable.ajay_devgn,
            name = "Ajay Bhai",
            time = "Today, 1:00 PM",
            isMissed = false
        ), Call(
            image = R.drawable.carryminati,
            name = "Carry",
            time = "Yesterday, 6:30 PM",
            isMissed = true
        )
    )

    var isSearching by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(

        topBar = {

            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (isSearching) {
                        TextField(
                            value = search,
                            onValueChange = { search = it },
                            placeholder = { Text("Search") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    } else {
                        Text(
                            text = "Calls",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        )
                    }

                    if (isSearching) {
                        IconButton(onClick = {
                            isSearching = false
                            search = ""
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.cross),
                                contentDescription = null
                            )
                        }
                    } else {

                        IconButton(onClick = { isSearching = true }) {
                            Icon(
                                painter = painterResource(R.drawable.search),
                                contentDescription = null
                            )
                        }

                        Box {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.more),
                                    contentDescription = null
                                )
                            }

                            DropdownMenu(
                                expanded = showMenu, onDismissRequest = { showMenu = false }) {
                                DropdownMenuItem(
                                    text = { Text("Settings") },
                                    onClick = { showMenu = false })
                            }
                        }
                    }
                }

                HorizontalDivider(thickness = 0.5.dp)
            }
        },

        bottomBar = {
            BottomNavigation()
        },


        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = colorResource(id = R.color.light_green),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_call),
                    contentDescription = "Camera"
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // 🔹 Favorites section
            FavoriteSection()

            // 🔹 Button
            Button(
                onClick = {}, colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.light_green)
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Start a new call",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Recent Calls",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f), // ✅ scroll fix
                contentPadding = PaddingValues(bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(sampleCall) { data ->
                    CallItemDesign(data)
                }
            }
        }
    }
}