package com.bangkit.coffee.presentation.profile

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents ProfileScreen
 **/
class ProfileState

/**
 * Profile Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ProfileActions(
    val updateAvatar: (Uri) -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalProfileActions = staticCompositionLocalOf<ProfileActions> {
    error("{NAME} Actions Were not provided, make sure ProvideProfileActions is called")
}

@Composable
fun ProvideProfileActions(actions: ProfileActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalProfileActions provides actions) {
        content.invoke()
    }
}

