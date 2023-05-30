package com.bangkit.coffee.presentation.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.bangkit.coffee.presentation.profile.components.ChangePasswordForm
import com.bangkit.coffee.presentation.profile.components.EditProfileForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow = MutableStateFlow(ProfileState())
    val stateFlow = _stateFlow.asStateFlow()

    fun updateAvatar(uri: Uri) {

    }

    fun openEditProfile() = _stateFlow.update {
        it.copy(editProfileVisible = true)
    }

    fun closeEditProfile() = _stateFlow.update {
        it.copy(editProfileVisible = false)
    }

    fun editProfile(form: EditProfileForm) {

    }

    fun openChangePassword() = _stateFlow.update {
        it.copy(changePasswordVisible = true)
    }

    fun closeChangePassword() = _stateFlow.update {
        it.copy(changePasswordVisible = false)
    }

    fun changePassword(form: ChangePasswordForm) {

    }
}