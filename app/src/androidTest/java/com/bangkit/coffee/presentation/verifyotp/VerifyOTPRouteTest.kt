package com.bangkit.coffee.presentation.verifyotp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.bangkit.coffee.MainActivity
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class VerifyOTPRouteTest  {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun should_disableButton_when_formClean() {
        rule.activity.setContent { AppTest { VerifyOTPRoute() } }

        rule.onNodeWithTag("VerifyOTPButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.activity.setContent { AppTest { VerifyOTPRoute() } }

        // Fill OTP
        rule.onNodeWithTag("OTPField").assertIsFocused()
        rule.onNodeWithTag("OTPField").performTextInput("123456")
        rule.onNodeWithTag("OTPField").performImeAction()

        // Form Conductor Bug
        rule.onNodeWithTag("OTPField").performTextReplacement("654321")

        rule.onNodeWithTag("VerifyOTPButton").assertIsEnabled()
    }
}