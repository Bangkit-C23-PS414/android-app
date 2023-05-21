package com.bangkit.coffee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.coffee.presentation.components.SimpleScreen
import com.bangkit.coffee.presentation.dashboard.DashboardScreen
import com.bangkit.coffee.presentation.forgotpassword.ForgotPasswordRoute
import com.bangkit.coffee.presentation.resetpassword.ResetPasswordRoute
import com.bangkit.coffee.presentation.signin.SignInRoute
import com.bangkit.coffee.presentation.signup.SignUpRoute
import com.bangkit.coffee.presentation.verifyotp.VerifyOTPRoute
import com.bangkit.coffee.presentation.welcome.WelcomeRoute

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
        startDestination = Graph.Splash.route,
    ) {
        // Splash
        composable(Graph.Splash.route) {
            SimpleScreen(
                text = "Splash",
                action = { navController.navigate(Graph.Dashboard.route) }
            )
        }

        // Welcome
        composable(Graph.Welcome.route) {
            WelcomeRoute(
                navigateToSignIn = { navController.navigate(Graph.SignIn.route) },
                navigateToSignUp = { navController.navigate(Graph.SignUp.route) }
            )
        }

        // Auth
        composable(Graph.SignIn.route) {
            SignInRoute(
                navigateUp = { navController.navigateUp() },
                navigateToForgotPassword = { navController.navigate(Graph.ForgotPassword.route) },
                navigateToDashboard = {
                    navController.navigate(Graph.Dashboard.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Graph.SignUp.route) {
            SignUpRoute(
                navigateUp = { navController.navigateUp() },
                navigateToDashboard = {
                    navController.navigate(Graph.Dashboard.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Forgot Password
        composable(Graph.ForgotPassword.route) {
            ForgotPasswordRoute(
                navigateUp = { navController.navigateUp() },
                navigateToVerifyOTP = { navController.navigate(Graph.VerifyOTP.route) }
            )
        }

        composable(Graph.VerifyOTP.route) {
            VerifyOTPRoute(
                navigateUp = { navController.navigateUp() },
                navigateToResetPassword = { navController.navigate(Graph.ResetPassword.route) }
            )
        }

        composable(Graph.ResetPassword.route) {
            ResetPasswordRoute(
                navigateUp = { navController.navigateUp() },
                navigateToLogin = {
                    navController.navigate(Graph.SignIn.route) {
                        popUpTo(Graph.Welcome.route)
                    }
                }
            )
        }

        // Dashboard
        composable(Graph.Dashboard.route) {
            DashboardScreen(
                rootNavController = navController,
                navController = rememberNavController()
            )
        }

        // Image detections detail
        composable(
            Graph.ImageDetectionDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("userId")
            SimpleScreen(text = "Detail Detection: $id")
        }
    }
}