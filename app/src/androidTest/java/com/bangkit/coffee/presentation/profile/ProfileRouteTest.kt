package com.bangkit.coffee.presentation.profile

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.ui.theme.AppTheme
import org.junit.Test

class ProfileRouteTest : ComposeTest() {

    @Test
    fun should_showAvatar_when_loaded() {
        rule.setContent { AppTheme { ProfileRoute() } }

        rule.onNodeWithTag("Avatar").assertIsDisplayed()
        rule.onNodeWithTag("Avatar").assertWidthIsAtLeast(150.dp)
        rule.onNodeWithTag("Avatar").assertHeightIsAtLeast(150.dp)

        rule.onNodeWithTag("UpdateAvatar").assertIsDisplayed()
        rule.onNodeWithTag("UpdateAvatar").assertHasClickAction()
    }

    @Test
    fun should_showEditProfile_when_clicked() {
        rule.setContent { AppTheme { ProfileRoute() } }

        rule.onNodeWithTag("EditProfile").assertHasClickAction()
        rule.onNodeWithTag("EditProfile").performClick()

        rule.onNode(isDialog()).assertExists()

        rule.onNodeWithTag("CloseButton").assertHasClickAction()
        rule.onNodeWithTag("CloseButton").performClick()
        rule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun should_changeEnableButton_when_editProfileFormChange() {
        rule.setContent { AppTheme { ProfileRoute() } }

        rule.onNodeWithTag("EditProfile").performClick()
        rule.onNodeWithTag("SaveButton").assertHasClickAction()

        rule.onNodeWithTag("NameField").performTextReplacement("John Doe")
        // Form Conductor Bug
        rule.onNodeWithTag("NameField").performTextReplacement("random")
        rule.onNodeWithTag("SaveButton").assertIsEnabled()

        rule.onNodeWithTag("NameField").performTextReplacement("")
        // Form Conductor Bug
        rule.onNodeWithTag("NameField").performTextReplacement("random")
        rule.onNodeWithTag("SaveButton").assertIsNotEnabled()
    }

    @Test
    fun should_showChangePassword_when_clicked() {
        rule.setContent { AppTheme { ProfileRoute() } }

        rule.onNodeWithTag("ChangePassword").assertHasClickAction()
        rule.onNodeWithTag("ChangePassword").performClick()
        rule.onNode(isDialog()).assertExists()

        rule.onNodeWithTag("CloseButton").assertHasClickAction()
        rule.onNodeWithTag("CloseButton").performClick()
        rule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun should_changeEnableButton_when_changePasswordFormChange() {
        rule.setContent { AppTheme { ProfileRoute() } }

        rule.onNodeWithTag("ChangePassword").performClick()
        rule.onNodeWithTag("SaveButton").assertIsNotEnabled()

        rule.onNodeWithTag("OldPasswordField").performTextInput("password")
        rule.onNodeWithTag("NewPasswordField").performTextInput("password")
        rule.onNodeWithTag("ConfirmNewPasswordField").performTextInput("password")
        // Form Conductor Bug
        rule.onNodeWithTag("ConfirmNewPasswordField").performTextInput("xx")
        rule.onNodeWithTag("SaveButton").assertIsEnabled()

        rule.onNodeWithTag("ConfirmNewPasswordField").performTextReplacement("passwordx")
        // Form Conductor Bug
        rule.onNodeWithTag("ConfirmNewPasswordField").performTextInput("xx")
        rule.onNodeWithTag("SaveButton").assertIsNotEnabled()
    }

    @Test
    fun should_showSignOutButton_when_loaded() {
        rule.setContent { AppTheme { ProfileRoute() } }

        rule.onNodeWithTag("SignOut").assertHasClickAction()
    }
}