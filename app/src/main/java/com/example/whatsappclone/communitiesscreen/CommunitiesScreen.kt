package com.example.whatsappclone.communitiesscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
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
fun CommunitiesScreen(navHostController: NavHostController) {

    var isSearching by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    val sampleCommunities = listOf(
        Communities(R.drawable.img, "Tech Enthusias", "264"),
        Communities(R.drawable.img, "Photography Lover", "222"),
        Communities(R.drawable.img, "Jetpack Compose", "121"),
    )

    val menuItems = listOf(
        TopBarMenuItem(
            label = "Settings",
            onClick = { showMenu = false }
        )
    )

    Scaffold(
        topBar = {
            SearchableScreenTopBar(
                title = "Communities",
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
                menuItems = menuItems
            )
        },
        bottomBar = {
            BottomNavigation(
                navHostController = navHostController,
                currentRoute = Routes.CommunitiesScreen
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Groups,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Start a new Community",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Communities",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            //  LIST
            LazyColumn {
                items(sampleCommunities) { community ->
                    CommunityItemDesign(communities = community)
                }
            }
        }
    }
}
