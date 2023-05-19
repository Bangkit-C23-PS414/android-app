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
                navigateUp = { navController.navigateUp() },
                navigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpRoute(
                navigateUp = { navController.navigateUp() },
            )
        }

        // Forgot Password
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordRoute(
                navigateUp = { navController.navigateUp() },
                navigateToVerifyOTP = { navController.navigate(Screen.VerifyOTP.route) }
            )
        }

        composable(Screen.VerifyOTP.route) {
            VerifyOTPRoute(
                navigateUp = { navController.navigateUp() },
                navigateToResetPassword = { navController.navigate(Screen.ResetPassword.route) }
            )
        }

        composable(Screen.ResetPassword.route) {
            ResetPasswordRoute(
                navigateUp = { navController.navigateUp() },
            )
        }

        // Image Detections
        composable(Screen.ImageDetections.route) {
            SimpleScreen(
                text = "List Detections",
                action = {
                    navController.navigate(
                        Screen.ImageDetectionDetail.createRoute("42")
                    )
                }
            )
        }

        composable(
            Screen.ImageDetectionDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("userId")
            SimpleScreen(text = "Detail Detection: $id")
        }
    }
}