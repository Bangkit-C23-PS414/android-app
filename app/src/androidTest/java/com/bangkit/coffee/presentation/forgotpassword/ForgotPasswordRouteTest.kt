package com.bangkit.coffee.presentation.forgotpassword

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.ui.theme.AppTheme
import org.junit.Test

class ForgotPasswordRouteTest : ComposeTest() {

    @Test
    fun should_disableButton_when_formClean() {
        rule.setContent { AppTheme { ForgotPasswordRoute() } }

        rule.onNodeWithTag("ForgotPasswordButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.setContent { AppTheme { ForgotPasswordRoute() } }

        // Fill email
        rule.onNodeWithTag("EmailField").assertIsNotFocused()
        rule.onNodeWithTag("EmailField").performTextInput("test@gmail.com")
        rule.onNodeWithTag("EmailField").performImeAction()

        // Form Conductor Bug
        rule.onNodeWithTag("EmailField").performTextReplacement("test2@gmail.com")

        rule.onNodeWithTag("ForgotPasswordButton").assertIsEnabled()
    }
}