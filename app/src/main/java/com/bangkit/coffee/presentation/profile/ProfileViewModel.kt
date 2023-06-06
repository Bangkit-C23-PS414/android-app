package com.bangkit.coffee.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.data.repository.UserPreferencesRepository
import com.bangkit.coffee.presentation.profile.components.ChangePasswordForm
import com.bangkit.coffee.presentation.profile.components.EditProfileForm
import com.bangkit.coffee.shared.wrapper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val imageDetectionRepository: ImageDetectionRepository
) : ViewModel() {

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

    fun signOut(){
        viewModelScope.launch {
            userPreferencesRepository.deleteToken()
            imageDetectionRepository.deleteAll()
            Log.d("SignOutViewModel", "token deleted")

            _stateFlow.update {
                it.copy(
                    signedOut = true,
                    message = Event("Signed out")
                )
            }
        }
    }
}