package com.example.whatsappclone.userregistrationscreen

import androidx.compose.foundation.layout.Box
import com.example.whatsappclone.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import org.w3c.dom.Text

@Composable
@Preview(showSystemUi = true)

fun UserRegistrationScreen() {

    var expanded by remember {
        mutableStateOf(value = false)
    }

    var selectedCountry by remember {
        mutableStateOf(value = "india")
    }

    var countryCode by remember {
        mutableStateOf(value = "+91")
    }

    var phoneNumber by remember {
        mutableStateOf(value = "")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Enter Your Phone Number",
            fontSize = 20.sp,
            color = colorResource(id = R.color.Dark_green),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(12.dp))

        Row {
            Text(
                text = "whatsapp will need to verify your phone number",
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "Whats",
                color = colorResource(id = R.color.light_green)
            )
        }
        Row {
            Text(
                text = "my number?",
                color = colorResource(id = R.color.light_green)
            )
        }
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth())
        {
            Box(
                modifier = Modifier.fillMaxWidth(),
                //contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    tint = colorResource(id = R.color.light_green)
                    )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 66.dp),
            color = colorResource(R.color.light_green),
            thickness = 2.dp
        )
        DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
            listOf("india", "USA" , "Canada", "China").forEach { country ->

                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = {
                        selectedCountry = country
                        expanded = false
                    },
                )
            }

        }


        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                TextField(
                    value = countryCode,
                    onValueChange = {
                        countryCode = it
                    },
                    modifier = Modifier.width(70.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,

                        unfocusedIndicatorColor = colorResource(R.color.light_green),
                        focusedIndicatorColor =  colorResource(R.color.light_green)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))


                TextField(value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    placeholder = { Text(text = "Phone Number ")},
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,

                        unfocusedIndicatorColor = colorResource(R.color.light_green),
                        focusedIndicatorColor =  colorResource(R.color.light_green)
                    )
                    )
            }
        }

        }

    }
