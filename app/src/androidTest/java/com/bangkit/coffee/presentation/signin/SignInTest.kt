package com.bangkit.coffee.presentation.signin

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.bangkit.coffee.presentation.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class SignInTest {

    @get:Rule
    val rule = createComposeRule()

    private fun setContent() {
        rule.setContent {
            AppTheme {
                SignInRoute(
                    navigateUp = mock(),
                    navigateToForgotPassword = mock()
                )
            }
        }
    }

    @Test
    fun should_disableButton_when_formClean() {
        setContent()

        rule.onNodeWithTag("SignInButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        setContent()

        rule.onNodeWithTag("EmailField").assertIsNotFocused()
        rule.onNodeWithTag("EmailField").performClick()
        rule.onNodeWithTag("EmailField").performTextInput("test@gmail.com")
        rule.onNodeWithTag("EmailField").performImeAction()

        rule.onNodeWithTag("SignInButton").assertIsNotEnabled()

        rule.onNodeWithTag("PasswordField").assertIsFocused()
        rule.onNodeWithTag("PasswordField").performTextInput("password")
        rule.onNodeWithTag("PasswordField").performImeAction()

        rule.onNodeWithTag("SignInButton").assertIsEnabled()
    }
}