package com.bangkit.coffee.util

import androidx.compose.runtime.Composable
import com.bangkit.coffee.app.ProvideRecoffeeryAppActions
import com.bangkit.coffee.app.RecoffeeryAppActions
import com.bangkit.coffee.shared.theme.AppTheme

@Composable
fun AppTest(
    content: @Composable () -> Unit
) {
    ProvideRecoffeeryAppActions(actions = RecoffeeryAppActions()) {
        AppTheme {
            content.invoke()
        }
    }
}