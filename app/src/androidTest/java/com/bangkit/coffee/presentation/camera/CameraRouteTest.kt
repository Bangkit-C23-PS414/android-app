package com.bangkit.coffee.presentation.camera

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.filters.FlakyTest
import androidx.test.rule.GrantPermissionRule
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.util.AppTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class CameraRouteTest : ComposeTest() {

    /**
     * Excecute this on adb shell
     * adb shell pm grant com.bangkit.coffee android.permission.CAMERA
     */

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA
    )

    @Test
    fun should_showCameraPreview_when_loaded() {
        rule.setContent { AppTest { CameraRoute() } }

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

    @Test
    @FlakyTest
    fun should_capture_when_captureButtonClicked() {
        rule.setContent { AppTest { CameraRoute() } }

        rule.waitUntilExactlyOneExists(hasTestTag("CameraPreview"))
        rule.onNodeWithTag("CaptureButton").performClick()
        rule.waitUntilExactlyOneExists(hasTestTag("CameraConfirm"), 10_000)

        rule.onNodeWithTag("DeleteImage").assertHasClickAction()
        rule.onNodeWithTag("Upload").assertHasClickAction()
    }
}