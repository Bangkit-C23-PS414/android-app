package com.bangkit.coffee.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bangkit.coffee.ui.components.SimpleScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = "sign-in",
        route = "auth",
    ) {
        composable("sign-in") {
            SimpleScreen(text = "Sign In")
        }

        composable("sign-up") {
            SimpleScreen(text = "Sign Up")
        }
    }
}