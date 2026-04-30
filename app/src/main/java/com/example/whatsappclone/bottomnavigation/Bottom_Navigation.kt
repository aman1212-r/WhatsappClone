package com.example.whatsappclone.bottomnavigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.navigation.Routes

@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    currentRoute: Routes
) {

    val items = listOf(
        BottomNavigationItemData("Chats", R.drawable.chat_icon, Routes.HomeScreen),
        BottomNavigationItemData("Updates", R.drawable.update_icon, Routes.UpdateScreen),
        BottomNavigationItemData("Communities", R.drawable.communities_icon, Routes.CommunitiesScreen),
        BottomNavigationItemData("Calls", R.drawable.telephone, Routes.CallScreen)
    )

    BottomAppBar(
        modifier = Modifier.navigationBarsPadding(),
        tonalElevation = 12.dp,
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEach { item ->
                val isSelected = currentRoute::class == item.route::class
                val tint = if (isSelected) {
                    colorResource(R.color.light_green)
                } else {
                    Color.Gray
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (currentRoute::class != item.route::class) {
                                navHostController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navHostController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        }
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp),
                        tint = tint
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = tint
                    )
                }
            }
        }
    }
}

private data class BottomNavigationItemData(
    val label: String,
    val icon: Int,
    val route: Routes
)
