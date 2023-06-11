package com.bangkit.coffee.presentation.welcome

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.bangkit.coffee.MainActivity
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class WelcomeRouteTest  {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun signInButton_exists() {
        rule.activity.setContent { AppTest { WelcomeRoute() } }

        rule.onNodeWithTag("SignInButton").assertHasClickAction()
        rule.onNodeWithTag("SignInButton").assertIsEnabled()
        rule.onNodeWithTag("SignInButton").assertIsDisplayed()
    }

    @Test
    fun signUpButton_exists() {
        rule.activity.setContent { AppTest { WelcomeRoute() } }

        rule.onNodeWithTag("SignUpButton").assertHasClickAction()
        rule.onNodeWithTag("SignUpButton").assertIsEnabled()
        rule.onNodeWithTag("SignUpButton").assertIsDisplayed()
    }
}