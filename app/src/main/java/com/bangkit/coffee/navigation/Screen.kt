package com.bangkit.coffee.navigation

sealed class Screen(val route: String) {
    object SignIn : Screen("sign-in")
    object SignUp : Screen("sign-up")
    object Dashboard : Screen("dashboard")
    object DetectionDetail : Screen("detections/{id}") {
        fun createRoute(id: String) = "detections/$id"
    }
}