package com.bangkit.coffee.presentation.welcome

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.onNodeWithTag
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.util.AppTest
import org.junit.Test

class WelcomeRouteTest : ComposeTest() {

    @Test
    fun signInButton_exists() {
        rule.setContent { AppTest { WelcomeRoute() } }

        rule.onNodeWithTag("SignInButton").assertHasClickAction()
        rule.onNodeWithTag("SignInButton").assertIsEnabled()
        rule.onNodeWithTag("SignInButton").assertIsDisplayed()
    }

    @Test
    fun signUpButton_exists() {
        rule.setContent { AppTest { WelcomeRoute() } }

        rule.onNodeWithTag("SignUpButton").assertHasClickAction()
        rule.onNodeWithTag("SignUpButton").assertIsEnabled()
        rule.onNodeWithTag("SignUpButton").assertIsDisplayed()
    }
}