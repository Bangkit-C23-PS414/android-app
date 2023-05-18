package com.bangkit.coffee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import imageDetectionGraph

@Composable
fun Router(
    navController: NavHostController = rememberNavController(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        tokenViewModel.isValid.collect {
            // navController.navigate("login")
        }
    }

    NavHost(
        navController = navController,
        startDestination = "auth",
    ) {
        authGraph(navController)
        imageDetectionGraph(navController)
    }
}