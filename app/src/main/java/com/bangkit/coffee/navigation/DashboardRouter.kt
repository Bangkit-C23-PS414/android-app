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
        route = Graph.Dashboard.route,
        navController = navController,
        startDestination = Graph.Home.route,
    ) {
        composable(Graph.Home.route) {
            HomeRoute()
        }
        composable(Graph.History.route) {
            HistoryRoute(
                navigateToDetail = {
                    rootNavController.navigate(
                        Graph.ImageDetectionDetail.createRoute("42")
                    )
                }
            )
        }
        composable(Graph.Profile.route) {
            ProfileRoute()
        }
    }
}