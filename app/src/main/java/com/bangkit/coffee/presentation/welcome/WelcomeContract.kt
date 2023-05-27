package com.bangkit.coffee.presentation.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents WelcomeScreen
 **/
data class WelcomeState(
    val carouselItems: List<WelcomeCarouselItem> = emptyList(),
)

/**
 * UI Event that represents WelcomeScreen
 */
sealed class WelcomeEvent {

}

data class WelcomeCarouselItem(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
)

/**
 * Welcome Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class WelcomeActions(
    val navigateToSignIn: () -> Unit = {},
    val navigateToSignUp: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalWelcomeActions = staticCompositionLocalOf<WelcomeActions> {
    error("{NAME} Actions Were not provided, make sure ProvideWelcomeActions is called")
}

@Composable
fun ProvideWelcomeActions(actions: WelcomeActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalWelcomeActions provides actions) {
        content.invoke()
    }
}

