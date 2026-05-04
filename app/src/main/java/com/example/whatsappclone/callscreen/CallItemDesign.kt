package com.example.whatsappclone.callscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.CallMade
import androidx.compose.material.icons.automirrored.rounded.CallMissed
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource


@Composable
fun CallItemDesign(call: Call) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            painter = painterResource(call.image),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(shape = CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier
                .width(12.dp)
        )

        Column() {

            Text(
                text = call.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold

            )

            Row() {
                Icon(
                    imageVector = if (call.isMissed) Icons.AutoMirrored.Rounded.CallMissed else Icons.AutoMirrored.Rounded.CallMade,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp),
                    tint = if (call.isMissed) Color.Red else colorResource(R.color.light_green)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = call.time,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = {} , modifier = Modifier
                .align(Alignment.CenterEnd)) {

                Icon(
                    imageVector = Icons.Rounded.Call,
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp),
                    tint = colorResource(R.color.light_green)
                )
            }
        }
    }


}
