package com.example.whatsappclone.viewmodels

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.DI.PhoneAuthUser
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject


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

                Log.d("PhoneAuth", "oneCodeSent triggered.Verification ID : $id")
                _authState.value = AuthState.CodeSent(verificationId = id)


            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signWithCredential(credential, context = activity)


            }

            override fun onVerificationFailed(exception: FirebaseException) {

                Log.e("PhoneAuth", "Verification failed:${exception.message}")
                _authState.value = AuthState.Error(exception.message ?: "Verification failed  ")
            }


        }


        // tells firebase when we need to send the OTP.


        val phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(option).build()

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)


    }

    private fun signWithCredential(credential: PhoneAuthCredential, context: Context) {

        _authState.value = AuthState.Loading


        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val phoneAuthUser = PhoneAuthUser(

                        // if user is null and user id is null then it store empty String

                        userId = user?.uid ?: "",
                        phoneNumber = user?.phoneNumber ?: "",
                    )

                    // we can check if user open WhatsApp for first time the UserRegistrationScreen
                    // will show and user is already register then we have to pass this screen

                    markUserAsSignedIn(context)
                    _authState.value = AuthState
                        .Success(phoneAuthUser)

                    fetchUserProfile(user?.uid ?: "")


                } else {


                    _authState.value = AuthState
                        .Error("Sign-in failed")
                }
            }
    }


    // 👀markUserAsSignedIn functions
    // we can use anny app data with Context
    fun markUserAsSignedIn(context: Context) {

        val sharedPreferences = context
            .getSharedPreferences("user_data", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putBoolean("isSignedIn", true).apply()
    }


    // 👀FetchUserProfile Function
    fun fetchUserProfile(userId: String) {

        val userRef = userRef.child(userId)
        userRef.get().addOnSuccessListener { snapshot ->

            if (snapshot.exists()) {

                val userProfile = snapshot.getValue(PhoneAuthUser::class.java)

                if (userProfile != null) {
                    _authState.value = AuthState.Success(userProfile)
                }

            }


        }.addOnFailureListener {

            _authState.value = AuthState.Error("Failed to fetch user profile")
        }


    }

    // 👾OTP verification

    fun verifyCode(otp: String, context: Context) {

        val currentAuthState = _authState.value

        if (currentAuthState !is AuthState.CodeSent || currentAuthState.verificationId.isEmpty()) {

            Log.e("PhoneAuth", "Attempting to verify OTP without a valid verification ID")

            _authState.value = AuthState.Error("Invalid verification ID")
            return

        }

        val credential = PhoneAuthProvider.getCredential(currentAuthState.verificationId, otp)
        signWithCredential(credential, context)

    }

    //convertBitmapToBase64 use for convert the profile picture to string and store in the database
    //and make string to the profile picture this conversion is done by BitMap
    // base64 is used to convert binary data into  Text.


    fun saveUserProfile(userId: String, name: String, status: String, profileImage: Bitmap?) {

        val database = FirebaseDatabase.getInstance().reference


        val encodedImage = profileImage?.let { convertBitmapToBase64(it) }
        val userProfile = PhoneAuthUser(
            userId = userId,
            name = name,
            Status = status,
            phoneNumber = Firebase.auth.currentUser?.phoneNumber ?: "",
            profileImage = encodedImage ?: ""
        )

        database.child("users").child(userId).setValue(userProfile)

    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)


        // this  variable convert the image in the raw data .


        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)

    }

    fun resetAuthState() {
        _authState.value = AuthState.Ideal
    }

    fun signOut(activity: Activity) {
        firebaseAuth.signOut()
        val sharedPreference = activity.getSharedPreferences("app_Prefs", Activity.MODE_PRIVATE)
        sharedPreference.edit()
            .putBoolean("isSignedIn", false).apply()
    }


}


sealed class AuthState {

    object Ideal : AuthState()
    object Loading : AuthState()

    data class CodeSent(val verificationId: String) : AuthState()
    data class Success(val user: PhoneAuthUser) : AuthState()
    data class Error(val message: String) : AuthState()

}
