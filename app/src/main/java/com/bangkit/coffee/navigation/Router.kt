package com.bangkit.coffee.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import detectionGraph

@Composable
fun Router(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
    ) {
        authGraph(navController)
        detectionGraph(navController)
    }
}