package com.bangkit.coffee.presentation.history

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.ui.theme.AppTheme
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class HistoryRouteTest : ComposeTest() {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun should_showListImageDetection_when_loaded() {
        rule.setContent { AppTheme { HistoryRoute() } }

        rule.waitUntilAtLeastOneExists(hasTestTag("ImageDetectionCard"))
        rule.onAllNodesWithTag("ImageDetectionCard").assertAll(hasClickAction())
    }

    @Test
    fun should_showFilterBottomSheet_when_filterClicked() {
        rule.setContent { AppTheme { HistoryRoute() } }

        rule.onNodeWithTag("FilterButton").assertHasClickAction()
        rule.onNodeWithTag("FilterButton").assertIsEnabled()

        rule.onNodeWithTag("FilterButton").performClick()
        rule.waitUntilExactlyOneExists(hasTestTag("FilterHistoryBottomSheet"))

        rule.onNodeWithTag("ClearFilterButton").assertHasClickAction()
        rule.onNodeWithTag("ClearFilterButton").assertIsEnabled()

        rule.onNodeWithTag("ApplyFilterButton").assertHasClickAction()
        rule.onNodeWithTag("ApplyFilterButton").assertIsEnabled()

        rule.onNodeWithTag("AllTimeSelect").assertIsSelectable()
        rule.onNodeWithTag("DateRangeSelect").assertHasClickAction()

        rule.onAllNodesWithTag("DiseaseCheckbox").assertAll(isToggleable())
    }

    @Test
    fun should_closeDialog_when_clearFilterButtonClicked() {
        rule.setContent { AppTheme { HistoryRoute() } }

        rule.onNodeWithTag("FilterButton").performClick()
        rule.waitUntilExactlyOneExists(hasTestTag("FilterHistoryBottomSheet"))

        rule.onNodeWithTag("ClearFilterButton").performClick()
        rule.onNodeWithTag("FilterHistoryBottomSheet").assertDoesNotExist()
    }

    @Test
    fun should_closeDialog_when_applyFilterButtonClicked() {
        rule.setContent { AppTheme { HistoryRoute() } }

        rule.onNodeWithTag("FilterButton").performClick()
        rule.waitUntilExactlyOneExists(hasTestTag("FilterHistoryBottomSheet"))

        rule.onNodeWithTag("ApplyFilterButton").performClick()
        rule.onNodeWithTag("FilterHistoryBottomSheet").assertDoesNotExist()
    }

    @Test
    fun should_disableApplyFilterButton_when_allDiseaseOptionOff() {
        rule.setContent { AppTheme { HistoryRoute() } }

        rule.onNodeWithTag("FilterButton").performClick()
        rule.waitUntilExactlyOneExists(hasTestTag("FilterHistoryBottomSheet"))

        rule.onAllNodesWithTag("DiseaseCheckbox").apply {
            fetchSemanticsNodes().forEachIndexed { i, _ ->
                get(i).performClick()
            }
        }

        // Form Conductor Bug
        rule.onAllNodesWithTag("DiseaseCheckbox").onFirst().performClick()

        rule.onNodeWithTag("ApplyFilterButton").assertIsNotEnabled()
    }

    @Test
    fun should_openDateRangePicker_when_pickDateRangeClicked() {
        rule.setContent { AppTheme { HistoryRoute() } }

        rule.onNodeWithTag("FilterButton").performClick()
        rule.waitUntilExactlyOneExists(hasTestTag("FilterHistoryBottomSheet"))

        rule.onNodeWithTag("DateRangeSelect").performClick()
        rule.onNode(isDialog()).assertExists()

        rule.onNodeWithTag("CloseDialog").performClick()
        rule.onNode(isDialog()).assertDoesNotExist()
    }
}