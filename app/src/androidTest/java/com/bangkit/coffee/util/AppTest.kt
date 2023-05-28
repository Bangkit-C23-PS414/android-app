package com.bangkit.coffee.util

import androidx.compose.runtime.Composable
import com.bangkit.coffee.ui.KopintarAppActions
import com.bangkit.coffee.ui.ProvideKopintarAppActions
import com.bangkit.coffee.ui.theme.AppTheme

@Composable
fun AppTest(
    content: @Composable () -> Unit
) {
    ProvideKopintarAppActions(actions = KopintarAppActions()) {
        AppTheme {
            content.invoke()
        }
    }
}