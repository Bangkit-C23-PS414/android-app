package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.bangkit.coffee.MainActivity
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.ImageDetectionDummy
import com.bangkit.coffee.shared.util.toDateTimeString
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ImageDetectionDetailScreenTest  {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    private val imageDetection = ImageDetectionDummy
    private val disease = DiseaseDummy

    @Test
    fun should_showDiseaseName_when_sick() {
        rule.activity.setContent {
            AppTest {
                ImageDetectionDetailScreen(
                    state = ImageDetectionDetailState(
                        refreshing = false,
                        imageDetection = imageDetection,
                        disease = disease
                    )
                )
            }
        }

        rule.onNodeWithText(imageDetection.detectedAt.toDateTimeString(), true).assertExists()
        rule.onNodeWithText(disease.name).assertExists()
    }

    @Test
    fun should_showDiseaseName_when_healthy() {
        rule.activity.setContent {
            AppTest {
                ImageDetectionDetailScreen(
                    state = ImageDetectionDetailState(
                        refreshing = false,
                        imageDetection = imageDetection
                    )
                )
            }
        }

        rule.onNodeWithText(imageDetection.detectedAt.toDateTimeString(), true).assertExists()
        rule.onNodeWithText(rule.activity.getString(R.string.everything_is_fine)).assertExists()
    }
}