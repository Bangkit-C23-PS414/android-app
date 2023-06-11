package com.bangkit.coffee.presentation.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import com.bangkit.coffee.MainActivity
import com.bangkit.coffee.R
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeRouteTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun should_showDetectionSteps_when_loaded() {
        rule.activity.setContent { AppTest { HomeRoute() } }

        rule.onNodeWithText(rule.activity.getString(R.string.how_to_detect)).assertIsDisplayed()
        rule.onAllNodesWithTag("DetectionStepCard", useUnmergedTree = true).apply {
            assertAll(hasNoClickAction())
            assertCountEquals(3)
            filterToOne(hasAnyDescendant(hasText(rule.activity.getString(R.string.how_to_detect_step_1)))).assertExists()
            filterToOne(hasAnyDescendant(hasText(rule.activity.getString(R.string.how_to_detect_step_2)))).assertExists()
            filterToOne(hasAnyDescendant(hasText(rule.activity.getString(R.string.how_to_detect_step_3)))).assertExists()
        }
    }

    @Test
    fun should_showDetectNowButton_when_loaded() {
        rule.activity.setContent { AppTest { HomeRoute() } }

        rule.onNodeWithText(rule.activity.getString(R.string.detect_now)).assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.detect_now)).assertIsEnabled()
        rule.onNodeWithText(rule.activity.getString(R.string.detect_now)).assertHasClickAction()
        rule.onNodeWithText(rule.activity.getString(R.string.detect_now)).assertIsNotFocused()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun should_showListDisease_when_loaded() {
        rule.activity.setContent { AppTest { HomeRoute() } }

        rule.onNodeWithText(rule.activity.getString(R.string.how_to_detect)).assertIsDisplayed()
        rule.waitUntilAtLeastOneExists(hasTestTag("DiseaseCard"))
        rule.onAllNodesWithTag("DiseaseCard").assertAll(hasClickAction())
    }
}