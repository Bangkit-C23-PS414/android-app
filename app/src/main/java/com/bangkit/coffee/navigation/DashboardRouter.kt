package com.bangkit.coffee.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bangkit.coffee.presentation.history.HistoryRoute
import com.bangkit.coffee.presentation.home.HomeRoute
import com.bangkit.coffee.presentation.profile.ProfileRoute

@Composable
fun DashboardRouter(
    rootNavController: NavHostController,
    navController: NavHostController
) {
    NavHost(
        route = Screen.Dashboard.route,
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeRoute()
        }
        composable(Screen.History.route) {
            HistoryRoute(
                navigateToDetail = {
                    rootNavController.navigate(
                        Screen.ImageDetectionDetail.createRoute("42")
                    )
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileRoute()
        }
    }
}