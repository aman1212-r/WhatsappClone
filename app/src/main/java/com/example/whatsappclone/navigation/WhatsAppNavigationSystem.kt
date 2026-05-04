package com.example.whatsappclone.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.callscreen.CallScreen
import com.example.whatsappclone.chat_box.ChatRoomScreen
import com.example.whatsappclone.communitiesscreen.CommunitiesScreen
import com.example.whatsappclone.homescreen.Homescreen
import com.example.whatsappclone.profile.ProfileScreen
import com.example.whatsappclone.splashscreen.SplashScreen
import com.example.whatsappclone.updatescreen.UpdateScreen
import com.example.whatsappclone.userregistrationscreen.UserRegistrationScreen
import com.example.whatsappclone.viewmodels.BaseViewModel
import com.example.whatsappclone.welcomescreen.WelcomeScreen

@Composable
fun WhatsAppNavigationSystem() {

    val navController = rememberNavController()
    val homeBaseViewModel: BaseViewModel = viewModel()

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
            UserRegistrationScreen(navController)
        }

        composable<Routes.HomeScreen> {
            Homescreen(
                navHostController = navController,
                homeBaseViewModel = homeBaseViewModel
            )
        }

        composable<Routes.UpdateScreen> {
            UpdateScreen(navHostController = navController)
        }

        composable<Routes.CommunitiesScreen> {
            CommunitiesScreen(navHostController = navController)
        }

        composable<Routes.CallScreen> {
            CallScreen(navHostController = navController)
        }

        composable<Routes.ProfileScreen> {
            ProfileScreen(navHostController = navController )
        }

        composable<Routes.ChatRoom> { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.ChatRoom>()
            ChatRoomScreen(
                navHostController = navController,
                name = route.name,
                phoneNumber = route.phoneNumber,
                image = route.image,
                baseViewModel = homeBaseViewModel
            )
        }

        composable<Routes.SettingScreen> {
            ProfileScreen(navHostController = navController)
        }




    }
}


