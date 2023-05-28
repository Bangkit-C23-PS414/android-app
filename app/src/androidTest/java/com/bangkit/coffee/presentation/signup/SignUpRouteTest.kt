package com.bangkit.coffee.presentation.signup

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.ui.theme.AppTheme
import org.junit.Test

class SignUpRouteTest : ComposeTest() {

    @Test
    fun should_disableButton_when_formClean() {
        rule.setContent { AppTheme { SignUpRoute() } }

        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.setContent { AppTheme { SignUpRoute() } }

        // Fill full name
        rule.onNodeWithTag("FullNameField").assertIsNotFocused()
        rule.onNodeWithTag("FullNameField").performTextInput("John")
        rule.onNodeWithTag("FullNameField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Fill email
        rule.onNodeWithTag("EmailField").assertIsFocused()
        rule.onNodeWithTag("EmailField").performTextInput("test@gmail.com")
        rule.onNodeWithTag("EmailField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Fill password
        rule.onNodeWithTag("PasswordField").assertIsFocused()
        rule.onNodeWithTag("PasswordField").performTextInput("password")
        rule.onNodeWithTag("PasswordField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Fill confirm password
        rule.onNodeWithTag("ConfirmPasswordField").assertIsFocused()
        rule.onNodeWithTag("ConfirmPasswordField").performTextInput("password")
        rule.onNodeWithTag("ConfirmPasswordField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Check agreement
        rule.onNodeWithTag("AgreementCheckbox").assertIsToggleable()
        rule.onNodeWithTag("AgreementCheckbox").assertIsOff()
        rule.onNodeWithTag("AgreementCheckbox").performClick()
        rule.onNodeWithTag("AgreementCheckbox").assertIsOn()

        // Form Conductor Bug
        rule.onNodeWithTag("FullNameField").performTextReplacement("John Doe")

        rule.onNodeWithTag("SignUpButton").assertIsEnabled()
    }
}