package com.bangkit.coffee.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.coffee.presentation.camera.CameraRoute
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
import com.bangkit.coffee.ui.components.SimpleScreen

@Composable
fun Router(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.ImageDetectionDetail.route,
    ) {
        // Splash
        composable(Screen.Splash.route) {
            SimpleScreen(
                text = "Splash",
                action = { navController.navigate(Screen.Home.route) }
            )
        }

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
                        popUpTo(Screen.Welcome.route)
                    }
                }
            )
        }

        // Dashboard
        composable(Screen.Home.route) {
            HomeRoute(
                navigateToCamera = { navController.navigate(Screen.Camera.route) }
            )
        }
        composable(Screen.History.route) {
            HistoryRoute(
                navigateToDetail = {
                    navController.navigate(
                        Screen.ImageDetectionDetail.createRoute("42")
                    )
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileRoute()
        }

        // Image detections detail
        composable(
            Screen.ImageDetectionDetail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = "default-id"
            })
        ) {
            ImageDetectionDetailRoute()
        }

        // Camera
        composable(Screen.Camera.route) {
            CameraRoute(
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}