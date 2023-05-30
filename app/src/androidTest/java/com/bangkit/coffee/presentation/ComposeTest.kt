package com.bangkit.coffee.presentation

import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule

open class ComposeTest {

    @get:Rule
    val rule = createComposeRule()

    fun getString(@StringRes id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(id)
    }

    fun hasText(@StringRes id: Int): SemanticsMatcher {
        return androidx.compose.ui.test.hasText(getString(id))
    }

    fun hasText(
        text: String,
        substring: Boolean = false,
        ignoreCase: Boolean = false
    ): SemanticsMatcher {
        return androidx.compose.ui.test.hasText(text, substring, ignoreCase)
    }

}