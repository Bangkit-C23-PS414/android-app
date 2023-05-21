package com.bangkit.coffee.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector
import com.bangkit.coffee.R

sealed class Graph(val route: String) {
    open class DashboardGraph(
        route: String,
        val icon: ImageVector,
        @StringRes val title: Int,
    ) : Graph(route)

    // Splash
    object Splash : Graph("splash")

    // Welcome
    object Welcome : Graph("welcome")

    // Auth
    object SignIn : Graph("auth/sign-in")
    object SignUp : Graph("auth/sign-up")
    object ForgotPassword : Graph("auth/forgot-password")
    object VerifyOTP : Graph("auth/forgot-password/verify")
    object ResetPassword : Graph("auth/forgot-password/reset")

    // Dashboard
    object Dashboard : Graph("dashboard") {
        val screens = listOf(Home, History, Profile)
    }

    // Home
    object Home : DashboardGraph("home", Icons.Filled.Home, R.string.home)

    // Image Detections
    object History : DashboardGraph("history", Icons.Filled.Timeline, R.string.history)

    // Profile
    object Profile : DashboardGraph("profile", Icons.Filled.Person, R.string.profile)

    // Image Detections Detail
    object ImageDetectionDetail : Graph("image-detections/{id}") {
        fun createRoute(id: String) = "image-detections/$id"
    }
}