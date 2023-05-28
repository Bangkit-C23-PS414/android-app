package com.bangkit.coffee.util

import androidx.compose.runtime.Composable
import com.bangkit.coffee.app.KopintarAppActions
import com.bangkit.coffee.app.ProvideKopintarAppActions
import com.bangkit.coffee.shared.theme.AppTheme

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