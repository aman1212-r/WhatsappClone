package com.example.whatsappclone.userregistrationscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.whatsappclone.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showSystemUi = true)
fun UserRegistrationScreen() {

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Enter Your Phone Number",
            fontSize = 20.sp,
            color = colorResource(id = R.color.Dark_green),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(12.dp))

        Row {
            Text("WhatsApp will need to verify your phone number ")
            Text(
                text = "What's",
                color = colorResource(id = R.color.light_green)
            )
        }

        Row {
            Text(
                text = "my number?",
                color = colorResource(id = R.color.light_green)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* -------- Country Dropdown -------- */

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

                    Icon(
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
            modifier = Modifier.padding(horizontal = 66.dp),
            color = colorResource(R.color.light_green),
            thickness = 2.dp
        )

        /* -------- Phone Fields -------- */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {

                TextField(
                    value = countryCode,
                    onValueChange = { countryCode = it },
                    modifier = Modifier.width(70.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = colorResource(R.color.light_green),
                        focusedIndicatorColor = colorResource(R.color.light_green)
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text("Phone Number") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = colorResource(R.color.light_green),
                        focusedIndicatorColor = colorResource(R.color.light_green)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Carrier charges may apply",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.light_green)
                )
            ) {
                Text(
                    text = "Next",
                    fontSize = 16.sp
                )
            }
        }
    }
}