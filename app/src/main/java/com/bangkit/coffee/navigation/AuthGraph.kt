package com.bangkit.coffee.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bangkit.coffee.presentation.components.SimpleScreen
import com.bangkit.coffee.presentation.welcome.WelcomeRoute

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = "welcome",
        route = "auth",
    ) {
        composable("welcome") {
            WelcomeRoute()
        }

        composable("sign-in") {
            SimpleScreen(text = "Sign In")
        }

        composable("sign-up") {
            SimpleScreen(text = "Sign Up")
        }
    }
}