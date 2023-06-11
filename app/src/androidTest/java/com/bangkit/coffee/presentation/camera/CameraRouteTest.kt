package com.bangkit.coffee.presentation.camera

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.filters.FlakyTest
import androidx.test.rule.GrantPermissionRule
import com.bangkit.coffee.MainActivity
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CameraRouteTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    /**
     * Excecute this on adb shell
     * adb shell pm grant com.bangkit.coffee android.permission.CAMERA
     */

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA
    )

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun should_showCameraPreview_when_loaded() {
        rule.activity.setContent { AppTest { CameraRoute() } }

        rule.waitUntilExactlyOneExists(hasTestTag("CameraPreview"))

        rule.onNodeWithTag("PickGalleryButton").assertHasClickAction()
        rule.onNodeWithTag("PickGalleryButton").assertIsDisplayed()
        rule.onNodeWithTag("PickGalleryButton").assertIsEnabled()

        rule.onNodeWithTag("CaptureButton").assertHasClickAction()
        rule.onNodeWithTag("CaptureButton").assertIsDisplayed()
        rule.onNodeWithTag("CaptureButton").assertIsEnabled()

        rule.onNodeWithTag("FlashToggle").assertHasClickAction()
        rule.onNodeWithTag("FlashToggle").assertIsDisplayed()
        rule.onNodeWithTag("FlashToggle").assertIsToggleable()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    @FlakyTest
    fun should_capture_when_captureButtonClicked() {
        rule.activity.setContent { AppTest { CameraRoute() } }

        rule.waitUntilExactlyOneExists(hasTestTag("CameraPreview"))
        rule.onNodeWithTag("CaptureButton").performClick()
        rule.waitUntilExactlyOneExists(hasTestTag("CameraConfirm"), 10_000)

        rule.onNodeWithTag("DeleteImage").assertHasClickAction()
        rule.onNodeWithTag("Upload").assertHasClickAction()
    }
}