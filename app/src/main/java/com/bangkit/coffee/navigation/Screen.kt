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

    object Manifest {
        val topBarScreens = emptyList<Screen>()
        val bottomBarScreens = listOf(
            Home, History, Profile
        )

        val topBarRoutes = topBarScreens.map { it.route }
        val bottomBarRoutes = bottomBarScreens.map { it.route }
    }

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

    // Disease Detail
    object DiseaseDetail : Screen("diseases/{id}") {
        fun createRoute(id: String) = "diseases/$id"
    }

    // Image Detections Detail
    object ImageDetectionDetail : Screen("image-detections/{id}") {
        fun createRoute(id: String) = "image-detections/$id"
    }

    // Camera
    object Camera : Screen("camera")
}