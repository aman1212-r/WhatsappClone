package com.example.whatsappclone.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.DI.PhoneAuthUser
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase,
) : ViewModel() {


    private val _authState = MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState = _authState.asStateFlow()


    // Node : used to save the user data.

    private val userRef = database.reference.child("users")

    fun senVerificationCode(phoneNumber: String, activity: Activity) {

        _authState.value = AuthState.Loading

        val option = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)

                // check weather the code is set or not

                Log.d("PhoneAuth", "oneCodeSent triggered.verfication ID : $id")
                _authState.value = AuthState.CodeSent(verificationId = id)


            }

            override fun onVerificationCompleted(Credential: PhoneAuthCredential) {


            }

            override fun onVerificationFailed(exception: FirebaseException) {

                Log.e("PhoneAuth", "Verfication failed:${exception.message}")
                _authState.value = AuthState.Error(exception.message ?: "Verification failed  ")
            }


        }


    }
}

sealed class AuthState {

    object Ideal : AuthState()
    object Loading : AuthState()

    data class CodeSent(val verificationId: String) : AuthState()
    data class Sucess(val user: PhoneAuthUser) : AuthState()
    data class Error(val message: String) : AuthState()

}


