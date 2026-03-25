package com.example.whatsappclone.callscreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.R

@Preview(showSystemUi = true)
@Composable
fun FavoriteSection() {

    val sampleFavorites = listOf(
        FavoriteContact(image = R.drawable.mrbeast, name = "Mr Beast"),
        FavoriteContact(image = R.drawable.bhuvan_bam, name = "Bhumi Bam"),
        FavoriteContact(image = R.drawable.rashmika, name = "Rashmi"),
        FavoriteContact(image = R.drawable.ajay_devgn, name = "ajay devgan"),
        FavoriteContact(image = R.drawable.carryminati, name = "Carry Minati"),
    )


    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(start = 16.dp, bottom = 8.dp)
    ) {

        Text(
            text = "Favorites",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {

            sampleFavorites.forEach { FavoritesItem(it) }
        }
    }

}

data class FavoriteContact(
    val image: Int,
    val name: String
)