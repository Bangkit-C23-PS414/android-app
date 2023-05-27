package com.bangkit.coffee.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileRoute(
    coordinator: ProfileCoordinator = rememberProfileCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberProfileActions(coordinator)

    // Handle events
    LaunchedEffect(Unit) {
        coordinator.screenEventFlow.collect { event ->
            when (event) {
                else -> {}
            }
        }
    }

    // UI Rendering
    ProfileScreen(uiState, actions)
}


@Composable
fun rememberProfileActions(coordinator: ProfileCoordinator): ProfileActions {
    return remember(coordinator) {
        ProfileActions(
            updateAvatar = coordinator::updateAvatar,
            openEditProfile = coordinator::openEditProfile,
            closeEditProfile = coordinator::closeEditProfile,
            editProfile = coordinator::editProfile,
            openChangePassword = coordinator::openChangePassword,
            closeChangePassword = coordinator::closeChangePassword,
            changePassword = coordinator::changePassword
        )
    }
}