package com.bangkit.coffee.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

@Composable
fun ProfileRoute(
    coordinator: ProfileCoordinator = rememberProfileCoordinator(),
    navigateToWelcome: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberProfileActions(
        coordinator,
        navigateToWelcome
    )

    // Handle events
    val appActions = LocalRecoffeeryAppActions.current
    if (uiState.signedOut) {
        LaunchedEffect(Unit) {
            actions.navigateToWelcome()
        }
    }
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }

    // UI Rendering
    ProfileScreen(uiState, actions)
}


@Composable
fun rememberProfileActions(
    coordinator: ProfileCoordinator,
    navigateToWelcome: () -> Unit,
): ProfileActions {
    return remember(coordinator) {
        ProfileActions(
            updateAvatar = coordinator::updateAvatar,
            openEditProfile = coordinator::openEditProfile,
            closeEditProfile = coordinator::closeEditProfile,
            editProfile = coordinator::editProfile,
            openChangePassword = coordinator::openChangePassword,
            closeChangePassword = coordinator::closeChangePassword,
            changePassword = coordinator::changePassword,
            signOut = coordinator::signOut,
            navigateToWelcome = navigateToWelcome
        )
    }
}