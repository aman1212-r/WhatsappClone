package com.example.whatsappclone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchableScreenTopBar(
    title: String,
    isSearching: Boolean,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchToggle: () -> Unit,
    menuExpanded: Boolean,
    onMenuToggle: () -> Unit,
    onMenuDismiss: () -> Unit,
    menuItems: List<TopBarMenuItem>,
    leadingActions: @Composable RowScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSearching) {
                TextField(
                    value = searchText,
                    onValueChange = onSearchTextChange,
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
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    color = Color(0xFF1D1D1F)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingActions()

                IconButton(onClick = onSearchToggle) {
                    Icon(
                        imageVector = if (isSearching) Icons.Rounded.Close else Icons.Rounded.Search,
                        contentDescription = if (isSearching) "Close Search" else "Search",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF1D1D1F)
                    )
                }

                Box {
                    IconButton(onClick = onMenuToggle) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "More",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF1D1D1F)
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = onMenuDismiss
                    ) {
                        menuItems.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item.label) },
                                onClick = item.onClick
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(color = Color(0xFFE8E8E8))
    }
}

data class TopBarMenuItem(
    val label: String,
    val onClick: () -> Unit
)
