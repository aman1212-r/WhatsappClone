package com.example.whatsappclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.callscreen.CallScreen
import com.example.whatsappclone.communitiesscreen.CommunitiesScreen
import com.example.whatsappclone.homescreen.Homescreen
import com.example.whatsappclone.splashscreen.SplashScreen
import com.example.whatsappclone.updatescreen.UpdateScreen
import com.example.whatsappclone.userregistrationscreen.UserRegistrationScreen
import com.example.whatsappclone.welcomescreen.WelcomeScreen

@Composable
fun WhatsAppNavigationSystem() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen
    ) {

        composable<Routes.SplashScreen> {
            SplashScreen(navController)
        }

        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.UserRegistrationScreen> {
            UserRegistrationScreen()
        }

        composable<Routes.HomeScreen> {
            Homescreen()
        }

        composable<Routes.UpdateScreen> {
            UpdateScreen()
        }

        composable<Routes.CommunitiesScreen> {
            CommunitiesScreen()
        }

        composable<Routes.CallScreen> {
            CallScreen()
        }




    }
}




