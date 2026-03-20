package com.example.whatsappclone.updatescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.bottomnavigation.BottomNavigation
import com.example.whatsappclone.R

@Composable
@Preview(showSystemUi = true)
fun UpdateScreen() {

    val sampleStatus = listOf(
        StatusData(R.drawable.bhuvan_bam, "Bhuvan Bam", "10:00"),
        StatusData(R.drawable.rashmika, "Rashmi", "11:00"),
        StatusData(R.drawable.rajkummar_rao, "Rajkumar Rao", "12:00"),
    )


    val sampleChannels = listOf(
        Channels(image = R.drawable.meta, name = "Meta", description = "Meta"),
        Channels(image = R.drawable.neat_roots, name = "neat_roots", description = "Teach News"),
    )

    Scaffold(
        topBar = { TopBar() },

        bottomBar = { BottomNavigation() },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = colorResource(id = R.color.light_green),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_camera_24),
                    contentDescription = "Camera"
                )
            }
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            item {
                Text(
                    text = "Status",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                MyStatus()
                sampleStatus.forEach { data ->
                    StatusItem(data)
                }
                HorizontalDivider(
                    color = Color.Gray
                )

                Text(
                    text = "Channels",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {

                    Text(
                        text = "stay updated on topics that matter to you. Find channels to follow bellow"
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Find channels to follow"
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )

                sampleChannels.forEach {
                    ChannelItemDesign(channels = it)
                }

            }

        }

    }
}