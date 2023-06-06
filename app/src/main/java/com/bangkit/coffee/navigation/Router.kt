package com.bangkit.coffee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.coffee.presentation.camera.CameraRoute
import com.bangkit.coffee.presentation.diseasedetail.DiseaseDetailRoute
import com.bangkit.coffee.presentation.forgotpassword.ForgotPasswordRoute
import com.bangkit.coffee.presentation.history.HistoryRoute
import com.bangkit.coffee.presentation.home.HomeRoute
import com.bangkit.coffee.presentation.imagedetectiondetail.ImageDetectionDetailRoute
import com.bangkit.coffee.presentation.profile.ProfileRoute
import com.bangkit.coffee.presentation.resetpassword.ResetPasswordRoute
import com.bangkit.coffee.presentation.signin.SignInRoute
import com.bangkit.coffee.presentation.signup.SignUpRoute
import com.bangkit.coffee.presentation.verifyotp.VerifyOTPRoute
import com.bangkit.coffee.presentation.welcome.WelcomeRoute

@Composable
fun Router(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Welcome.route,
    ) {
        // Welcome
        composable(Screen.Welcome.route) {
            WelcomeRoute(
                navigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                navigateToSignUp = { navController.navigate(Screen.SignUp.route) }
            )
        }

        // Auth
        composable(Screen.SignIn.route) {
            SignInRoute(
                navigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                navigateToDashboard = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpRoute(
                navigateToDashboard = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Forgot Password
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordRoute(
                navigateToVerifyOTP = { navController.navigate(Screen.VerifyOTP.route) }
            )
        }

        composable(Screen.VerifyOTP.route) {
            VerifyOTPRoute(
                navigateToResetPassword = { navController.navigate(Screen.ResetPassword.route) }
            )
        }

        composable(Screen.ResetPassword.route) {
            ResetPasswordRoute(
                navigateToLogin = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        // Dashboard
        composable(Screen.Home.route) {
            HomeRoute(
                navigateToCamera = { navController.navigate(Screen.Camera.route) },
                navigateToDetailDisease = { id ->
                    navController.navigate(
                        Screen.DiseaseDetail.createRoute(id)
                    )
                }
            )
        }

        composable(Screen.History.route) {
            HistoryRoute(
                navigateToDetailImageDetection = { id ->
                    navController.navigate(
                        Screen.ImageDetectionDetail.createRoute(id)
                    )
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileRoute(
                navigateToWelcome = { navController.navigate(Screen.Welcome.route)}
            )
        }

        // Disease Detail
        composable(
            Screen.DiseaseDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "default-id"
            })
        ) {
            DiseaseDetailRoute(
                navigateUp = { navController.navigateUp() }
            )
        }

        // Image detections Detail
        composable(
            Screen.ImageDetectionDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "default-id"
            })
        ) {
            ImageDetectionDetailRoute(
                navigateUp = { navController.navigateUp() }
            )
        }

        // Camera
        composable(Screen.Camera.route) {
            CameraRoute(
                navigateUp = { navController.navigateUp() },
                navigateToDetail = { id ->
                    // Go back
                    navController.navigateUp()

                    // Navigate to history
                    navController.navigate(Screen.History.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                    // Navigate to detail
                    navController.navigate(Screen.ImageDetectionDetail.createRoute(id))
                }
            )
        }
    }
}