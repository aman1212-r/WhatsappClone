package com.example.whatsappclone.userregistrationscreen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.navigation.Routes
import com.example.whatsappclone.viewmodels.AuthState
import com.example.whatsappclone.viewmodels.PhoneAuthViewModel

@Composable
fun UserRegistrationScreen(
    navController: NavHostController,
    phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {

    val authState by phoneAuthViewModel.authState.collectAsState()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeContentPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = if (verificationId == null)
                "Enter your phone number"
            else
                "Enter OTP",
            fontSize = 22.sp,
            color = colorResource(id = R.color.Dark_green),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        /*
        PHONE NUMBER SCREEN
        ONLY SHOW WHEN verificationId == null
        */

        if (verificationId == null) {

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

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {

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
                            text = {
                                Text(text = country)
                            },
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
                color = colorResource(id = R.color.light_green),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                TextField(
                    value = countryCode,
                    onValueChange = {
                        countryCode = it
                    },
                    modifier = Modifier.width(90.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.light_green),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    placeholder = {
                        Text("Phone Number")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.light_green),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (phoneNumber.isNotEmpty()) {

                        val fullPhoneNumber = "$countryCode$phoneNumber"

                        phoneAuthViewModel.sendVerificationCode(
                            fullPhoneNumber,
                            activity
                        )

                    } else {
                        Toast.makeText(
                            context,
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.Dark_green)
                )
            ) {
                Text("Send OTP")
            }

            if (authState is AuthState.Loading) {
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator()
            }
        }

        /*
        HANDLE AUTH STATE
        */

        when (authState) {

            is AuthState.CodeSent -> {
                verificationId =
                    (authState as AuthState.CodeSent).verificationId
            }

            is AuthState.Success -> {

                Log.d("PhoneAuth", "Login Success")

                phoneAuthViewModel.resetAuthState()

                navController.navigate(Routes.UserProfileScreen) {
                    popUpTo(Routes.UserRegistrationScreen) {
                        inclusive = true
                    }
                }
            }

            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }

        /*
        OTP SCREEN
        ONLY SHOW WHEN verificationId != null
        */

        if (verificationId != null) {

            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = otp,
                onValueChange = {
                    otp = it
                },
                placeholder = {
                    Text("OTP")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.light_green),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (otp.isNotEmpty() && verificationId != null) {

                        phoneAuthViewModel.verifyCode(
                            otp,
                            context
                        )

                    } else {
                        Toast.makeText(
                            context,
                            "Please Enter Valid OTP",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.Dark_green)
                )
            ) {
                Text("Verify OTP")
            }

            if (authState is AuthState.Loading) {
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator()
            }
        }
    }
}