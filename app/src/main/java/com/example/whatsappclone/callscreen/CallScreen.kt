package com.example.whatsappclone.callscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddIcCall
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.bottomnavigation.BottomNavigation
import com.example.whatsappclone.navigation.Routes
import com.example.whatsappclone.ui.components.SearchableScreenTopBar
import com.example.whatsappclone.ui.components.TopBarMenuItem

@Composable
fun CallScreen(navHostController: NavHostController) {

    val sampleCall = listOf(
        Call(
            image = R.drawable.bhuvan_bam,
            name = "Bhumi Bam",
            time = "Yesterday, 8:30 PM",
            isMissed = true
        ), Call(
            image = R.drawable.boy1,
            name = "Sharukh khan",
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
    val menuItems = listOf(
        TopBarMenuItem(
            label = "Settings",
            onClick = { showMenu = false }
        )
    )

    Scaffold(

        topBar = {
            SearchableScreenTopBar(
                title = "Calls",
                isSearching = isSearching,
                searchText = search,
                onSearchTextChange = { search = it },
                onSearchToggle = {
                    if (isSearching) {
                        isSearching = false
                        search = ""
                    } else {
                        isSearching = true
                    }
                },
                menuExpanded = showMenu,
                onMenuToggle = { showMenu = true },
                onMenuDismiss = { showMenu = false },
                menuItems = menuItems,
                leadingActions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.PhotoCamera,
                            contentDescription = "Camera",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        },

        bottomBar = {
            BottomNavigation(
                navHostController = navHostController,
                currentRoute = Routes.CallScreen
            )
        },


        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = colorResource(id = R.color.light_green),
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddIcCall,
                    contentDescription = "Start call",
                    modifier = Modifier.size(24.dp)
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
                    color = androidx.compose.ui.graphics.Color.White,
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
                modifier = Modifier.weight(1f), //  scroll
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
