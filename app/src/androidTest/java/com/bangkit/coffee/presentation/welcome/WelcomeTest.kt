package com.bangkit.coffee.presentation.welcome

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.bangkit.coffee.presentation.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class WelcomeTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private fun setContent() {
        rule.setContent {
            AppTheme {
                WelcomeRoute(
                    navigateToSignIn = mock(),
                    navigateToSignUp = mock()
                )
            }
        }
    }

    @Test
    fun signInButton_exists() {
        setContent()

        rule.onNodeWithTag("SignInButton").assertHasClickAction()
        rule.onNodeWithTag("SignInButton").assertIsEnabled()
        rule.onNodeWithTag("SignInButton").assertIsDisplayed()
    }

    @Test
    fun signUpButton_exists() {
        setContent()

        rule.onNodeWithTag("SignUpButton").assertHasClickAction()
        rule.onNodeWithTag("SignUpButton").assertIsEnabled()
        rule.onNodeWithTag("SignUpButton").assertIsDisplayed()
    }
}