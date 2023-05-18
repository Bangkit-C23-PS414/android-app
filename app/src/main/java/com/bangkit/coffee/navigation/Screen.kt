package com.bangkit.coffee.navigation

sealed class Screen(val route: String) {
    // Welcome
    object Welcome : Screen("welcome")

    // Auth
    object SignIn : Screen("auth/sign-in")
    object SignUp : Screen("auth/sign-up")
    object ForgotPassword : Screen("auth/forgot-password")
    object VerifyOTP : Screen("auth/forgot-password/verify")
    object ResetPassword : Screen("auth/forgot-password/reset")

    // Image Detections
    object ImageDetections : Screen("image-detections")
    object ImageDetectionDetail : Screen("image-detections/{id}") {
        fun createRoute(id: String) = "image-detections/$id"
    }
}