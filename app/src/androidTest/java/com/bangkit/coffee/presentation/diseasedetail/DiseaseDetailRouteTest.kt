package com.bangkit.coffee.presentation.diseasedetail

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.bangkit.coffee.MainActivity
import com.bangkit.coffee.R
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DiseaseDetailRouteTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun should_showDiseaseInfo_when_loaded() {
        rule.activity.setContent { AppTest { DiseaseDetailRoute() } }

        rule.waitUntilExactlyOneExists(
            hasText(rule.activity.getString(R.string.how_to_control)),
            10_000
        )
        /*val disease = checkNotNull(viewModel.stateFlow.value.disease)

        rule.onNodeWithText(disease.name).assertExists()
        rule.onNodeWithText(disease.description).assertExists()
        disease.controls.forEach { rule.onNodeWithText(it).assertExists() }*/
    }
}