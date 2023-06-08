package com.bangkit.coffee.presentation.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.data.repository.ProfileRepository
import com.bangkit.coffee.data.repository.SessionRepository
import com.bangkit.coffee.presentation.profile.components.ChangePasswordForm
import com.bangkit.coffee.presentation.profile.components.EditProfileForm
import com.bangkit.coffee.shared.wrapper.Event
import com.bangkit.coffee.shared.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val imageDetectionRepository: ImageDetectionRepository,
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(ProfileState())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            launch { refresh() }

            profileRepository.get().collect { user ->
                _stateFlow.update {
                    it.copy(user = user)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(refreshing = true) }
            val response = profileRepository.refresh()

            if (response is Resource.Error) {
                _stateFlow.update {
                    it.copy(message = Event(response.message))
                }
            }

            _stateFlow.update { it.copy(refreshing = false) }
        }
    }

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

    fun signOut() {
        viewModelScope.launch {
            sessionRepository.clearAll()
            imageDetectionRepository.deleteAll()

            _stateFlow.update {
                it.copy(
                    signedOut = true,
                    message = Event("Signed out")
                )
            }
        }
    }
}