package com.bangkit.coffee.presentation.resetpassword

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
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
class ResetPasswordRouteTest  {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun should_disableButton_when_formClean() {
        rule.activity.setContent { AppTest { ResetPasswordRoute() } }

        rule.onNodeWithTag("ResetPasswordButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.activity.setContent { AppTest { ResetPasswordRoute() } }

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

        // Form Conductor Bug
        rule.onNodeWithTag("NewPasswordField").performTextReplacement("passwordlong")

        rule.onNodeWithTag("ResetPasswordButton").assertIsEnabled()
    }
}