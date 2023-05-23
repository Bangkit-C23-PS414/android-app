package com.bangkit.coffee.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector
import com.bangkit.coffee.R

sealed class Screen(val route: String) {
    open class NavigationBarScreen(
        route: String,
        val icon: ImageVector,
        @StringRes val title: Int,
    ) : Screen(route)

    // Splash
    object Splash : Screen("splash")

    // Welcome
    object Welcome : Screen("welcome")

    // Auth
    object SignIn : Screen("auth/sign-in")
    object SignUp : Screen("auth/sign-up")
    object ForgotPassword : Screen("auth/forgot-password")
    object VerifyOTP : Screen("auth/forgot-password/verify")
    object ResetPassword : Screen("auth/forgot-password/reset")

    // Home
    object Home : NavigationBarScreen("home", Icons.Filled.Home, R.string.home)

    // Image Detections
    object History : NavigationBarScreen("history", Icons.Filled.Timeline, R.string.history)

    // Profile
    object Profile : NavigationBarScreen("profile", Icons.Filled.Person, R.string.profile)

    // Image Detections Detail
    object ImageDetectionDetail : Screen("image-detections/{id}") {
        fun createRoute(id: String) = "image-detections/$id"
    }

    // Camera
    object Camera : Screen("camera")
}