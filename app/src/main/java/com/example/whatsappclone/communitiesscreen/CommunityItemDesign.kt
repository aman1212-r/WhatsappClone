package com.example.whatsappclone.communitiesscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommunityItemDesign( communities: Communities) {

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = communities.image),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(18.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))


            Column() {

                Text(
                    text = communities.name ,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = communities.memberCount,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Rounded.Groups,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF1F4F2))
                    .padding(2.dp),
                tint = Color(0xFF168A4C)
            )
        }
    }
}

data class Communities(
    val image: Int,
    val name : String,
    val memberCount : String

    )
