package com.bangkit.coffee.presentation.signin

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.bangkit.coffee.presentation.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class SignInRouteTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun should_disableButton_when_formClean() {
        rule.setContent { AppTheme { SignInRoute() } }

        rule.onNodeWithTag("SignInButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.setContent { AppTheme { SignInRoute() } }

        // Fill email
        rule.onNodeWithTag("EmailField").assertIsNotFocused()
        rule.onNodeWithTag("EmailField").performTextInput("test@gmail.com")
        rule.onNodeWithTag("EmailField").performImeAction()
        rule.onNodeWithTag("SignInButton").assertIsNotEnabled()

        // Fill password
        rule.onNodeWithTag("PasswordField").assertIsFocused()
        rule.onNodeWithTag("PasswordField").performTextInput("password")
        rule.onNodeWithTag("PasswordField").performImeAction()

        // Final test
        rule.onNodeWithTag("PasswordField").performTextReplacement("longpassword")
        rule.onNodeWithTag("SignInButton").assertIsEnabled()
    }
}