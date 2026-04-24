package com.example.whatsappclone.userregistrationscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.viewmodels.PhoneAuthViewModel

@Composable
fun UserRegistrationScreen(navController: NavHostController, PhoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()) {

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Enter your phone number",
            fontSize = 20.sp,
            color = colorResource(id = R.color.Dark_green),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "WhatsApp will need to verify your phone number.",
            color = Color.Gray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "What's my number?",
            color = colorResource(id = R.color.light_green),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        text = selectedCountry,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        tint = colorResource(id = R.color.light_green)
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                val countries = listOf(
                    "India" to "+91",
                    "USA" to "+1",
                    "Canada" to "+1",
                    "China" to "+86"
                )

                countries.forEach { (country, code) ->

                    DropdownMenuItem(
                        text = { Text(country) },
                        onClick = {
                            selectedCountry = country
                            countryCode = code
                            expanded = false
                        }
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 52.dp),
            color = colorResource(R.color.light_green),
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = countryCode,
                    onValueChange = { countryCode = it },
                    modifier = Modifier.width(76.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = colorResource(R.color.light_green),
                        focusedIndicatorColor = colorResource(R.color.light_green)
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it.filter(Char::isDigit) },
                    placeholder = {
                        Text(
                            text = "phone number",
                            fontSize = 14.sp
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = colorResource(R.color.light_green),
                        focusedIndicatorColor = colorResource(R.color.light_green)
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Carrier charges may apply",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                ),
                modifier = Modifier.width(120.dp)
            ) {
                Text(
                    text = "Next",
                    fontSize = 16.sp
                )
            }
        }
    }
}
