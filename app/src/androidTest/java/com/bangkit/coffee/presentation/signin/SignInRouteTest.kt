package com.bangkit.coffee.presentation.signin

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
import com.bangkit.coffee.app.ProvideRecoffeeryAppActions
import com.bangkit.coffee.app.RecoffeeryAppActions
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SignInRouteTest  {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun should_disableButton_when_formClean() {
        rule.activity.setContent {
            ProvideRecoffeeryAppActions(actions = RecoffeeryAppActions()) {
                AppTest { SignInRoute() }
            }
        }

        rule.onNodeWithTag("SignInButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.activity.setContent {
            ProvideRecoffeeryAppActions(actions = RecoffeeryAppActions()) {
                AppTest { SignInRoute() }
            }
        }

        // Fill email
        rule.onNodeWithTag("EmailField").assertIsNotFocused()
        rule.onNodeWithTag("EmailField").performTextInput("test@gmail.com")
        rule.onNodeWithTag("EmailField").performImeAction()
        rule.onNodeWithTag("SignInButton").assertIsNotEnabled()

        // Fill password
        rule.onNodeWithTag("PasswordField").assertIsFocused()
        rule.onNodeWithTag("PasswordField").performTextInput("password")
        rule.onNodeWithTag("PasswordField").performImeAction()

        // Form Conductor Bug
        rule.onNodeWithTag("PasswordField").performTextReplacement("longpassword")

        rule.onNodeWithTag("SignInButton").assertIsEnabled()
    }
}