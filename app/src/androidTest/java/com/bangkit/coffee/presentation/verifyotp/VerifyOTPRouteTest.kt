package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.bangkit.coffee.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class VerifyOTPRouteTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun should_disableButton_when_formClean() {
        rule.setContent { AppTheme { VerifyOTPRoute() } }

        rule.onNodeWithTag("VerifyOTPButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.setContent { AppTheme { VerifyOTPRoute() } }

        // Fill OTP
        rule.onNodeWithTag("OTPField").assertIsFocused()
        rule.onNodeWithTag("OTPField").performTextInput("123456")
        rule.onNodeWithTag("OTPField").performImeAction()

        rule.onNodeWithTag("OTPField").performTextReplacement("654321")
        rule.onNodeWithTag("VerifyOTPButton").assertIsEnabled()
    }
}