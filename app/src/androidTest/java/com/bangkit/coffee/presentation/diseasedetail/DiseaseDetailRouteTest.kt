package com.bangkit.coffee.presentation.diseasedetail

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.ComposeTest
import com.bangkit.coffee.util.AppTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DiseaseDetailRouteTest : ComposeTest() {

    private lateinit var viewModel: DiseaseDetailViewModel

    @Before
    fun setUp() {
        val savedStateHandle = SavedStateHandle(mapOf("id" to "default-id"))
        viewModel = Mockito.mock(DiseaseDetailViewModel::class.java)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun should_showDiseaseInfo_when_loaded() {
        rule.setContent {
            AppTest {
                DiseaseDetailRoute(
                    coordinator = rememberDiseaseDetailCoordinator(viewModel)
                )
            }
        }

        rule.waitUntilExactlyOneExists(hasText(R.string.how_to_control), 10_000)
        val disease = checkNotNull(viewModel.stateFlow.value.disease)

        rule.onNodeWithText(disease.name).assertExists()
        rule.onNodeWithText(disease.description).assertExists()
        disease.controls.forEach { rule.onNodeWithText(it).assertExists() }
    }
}