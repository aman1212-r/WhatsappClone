package com.example.whatsappclone.profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.whatsappclone.viewmodels.PhoneAuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfileScreen(phoneAuthViewModel: PhoneAuthViewModel, navHostController: NavHostController) {


    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }


    val firebaseAuth = Firebase.auth
    val phoneNumber = firebaseAuth.currentUser?.phoneNumber ?: ""

    val userId = firebaseAuth.currentUser?.uid ?: ""
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->

            profileImageUri = uri

            uri?.let { }
        }
    ) {}
}