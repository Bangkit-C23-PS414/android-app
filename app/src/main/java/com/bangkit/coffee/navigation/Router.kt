package com.bangkit.coffee.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import imageDetectionGraph

@Composable
fun Router(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
    ) {
        authGraph(navController)
        imageDetectionGraph(navController)
    }
}