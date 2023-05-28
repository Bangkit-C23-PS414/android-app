package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.ui.test.onNodeWithText
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.ImageDetectionDummy
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.shared.util.toDateTimeString
import com.bangkit.coffee.util.AppTest
import org.junit.Test

class ImageDetectionDetailScreenTest : ComposeTest() {

    private val imageDetection = ImageDetectionDummy
    private val disease = DiseaseDummy

    @Test
    fun should_showDiseaseName_when_sick() {
        rule.setContent {
            AppTest {
                ImageDetectionDetailScreen(
                    state = ImageDetectionDetailState(
                        loading = false,
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
        rule.setContent {
            AppTest {
                ImageDetectionDetailScreen(
                    state = ImageDetectionDetailState(
                        loading = false,
                        imageDetection = imageDetection
                    )
                )
            }
        }

        rule.onNodeWithText(imageDetection.detectedAt.toDateTimeString(), true).assertExists()
        rule.onNodeWithText(getString(R.string.everything_is_fine)).assertExists()
    }
}