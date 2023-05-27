package com.bangkit.coffee.util

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.performTextInput

fun SemanticsNodeInteraction.performTextInputChars(text: String) {
    text.forEach { ch ->
        this.performTextInput(ch.toString())
    }
}