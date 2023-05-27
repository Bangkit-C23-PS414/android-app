package com.bangkit.coffee.presentation.profile

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.presentation.profile.components.ChangePasswordForm
import com.bangkit.coffee.presentation.profile.components.EditProfileForm


/**
 * UI State that represents ProfileScreen
 **/
data class ProfileState(
    val editProfileVisible: Boolean = false,
    val changePasswordVisible: Boolean = false,
)

/**
 * UI Event that represents ProfileScreen
 */
sealed class ProfileEvent {

}

/**
 * Profile Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ProfileActions(
    val updateAvatar: (Uri) -> Unit = {},
    val openEditProfile: () -> Unit = {},
    val closeEditProfile: () -> Unit = {},
    val editProfile: (EditProfileForm) -> Unit = {},
    val openChangePassword: () -> Unit = {},
    val closeChangePassword: () -> Unit = {},
    val changePassword: (ChangePasswordForm) -> Unit = {}
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

