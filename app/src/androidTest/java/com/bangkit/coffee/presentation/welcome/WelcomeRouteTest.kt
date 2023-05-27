package com.bangkit.coffee.presentation.welcome

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.bangkit.coffee.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class WelcomeRouteTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun signInButton_exists() {
        rule.setContent { AppTheme { WelcomeRoute() } }

        rule.onNodeWithTag("SignInButton").assertHasClickAction()
        rule.onNodeWithTag("SignInButton").assertIsEnabled()
        rule.onNodeWithTag("SignInButton").assertIsDisplayed()
    }

    @Test
    fun signUpButton_exists() {
        rule.setContent { AppTheme { WelcomeRoute() } }

        rule.onNodeWithTag("SignUpButton").assertHasClickAction()
        rule.onNodeWithTag("SignUpButton").assertIsEnabled()
        rule.onNodeWithTag("SignUpButton").assertIsDisplayed()
    }
}