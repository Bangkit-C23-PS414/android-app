package com.bangkit.coffee.presentation.resetpassword

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

class ResetPasswordRouteTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun should_disableButton_when_formClean() {
        rule.setContent { AppTheme { ResetPasswordRoute() } }

        rule.onNodeWithTag("ResetPasswordButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.setContent { AppTheme { ResetPasswordRoute() } }

        // Fill new password
        rule.onNodeWithTag("NewPasswordField").assertIsNotFocused()
        rule.onNodeWithTag("NewPasswordField").performTextInput("password")
        rule.onNodeWithTag("NewPasswordField").performImeAction()
        rule.onNodeWithTag("ResetPasswordButton").assertIsNotEnabled()

        // Fill confirm password
        rule.onNodeWithTag("ConfirmNewPasswordField").assertIsFocused()
        rule.onNodeWithTag("ConfirmNewPasswordField").performTextInput("password")
        rule.onNodeWithTag("ConfirmNewPasswordField").performImeAction()
        rule.onNodeWithTag("ResetPasswordButton").assertIsNotEnabled()

        rule.onNodeWithTag("NewPasswordField").performTextReplacement("passwordlong")
        rule.onNodeWithTag("ResetPasswordButton").assertIsEnabled()
    }
}