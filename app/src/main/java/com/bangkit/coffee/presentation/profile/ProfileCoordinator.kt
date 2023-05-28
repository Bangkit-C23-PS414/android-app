package com.bangkit.coffee.presentation.profile

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.coffee.presentation.profile.components.ChangePasswordForm
import com.bangkit.coffee.presentation.profile.components.EditProfileForm

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class ProfileCoordinator(
    val viewModel: ProfileViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun updateAvatar(uri: Uri) = viewModel.updateAvatar(uri)

    fun openEditProfile() = viewModel.openEditProfile()
    fun closeEditProfile() = viewModel.closeEditProfile()
    fun editProfile(form: EditProfileForm) = viewModel.editProfile(form)

    fun openChangePassword() = viewModel.openChangePassword()
    fun closeChangePassword() = viewModel.closeChangePassword()
    fun changePassword(form: ChangePasswordForm) = viewModel.changePassword(form)
}

@Composable
fun rememberProfileCoordinator(
    viewModel: ProfileViewModel = hiltViewModel()
): ProfileCoordinator {
    return remember(viewModel) {
        ProfileCoordinator(
            viewModel = viewModel
        )
    }
}