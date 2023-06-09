package com.bangkit.coffee.presentation.resetpassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.source.remote.AuthService
import com.bangkit.coffee.data.source.remote.model.ResetPasswordRequest
import com.bangkit.coffee.shared.wrapper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authService: AuthService
) : ViewModel() {

    private val token = checkNotNull<String>(savedStateHandle["token"])
    private val header = "Bearer $token"

    private val _stateFlow = MutableStateFlow(ResetPasswordState())
    val stateFlow = _stateFlow.asStateFlow()

    fun resetPassword(newPassword: String){
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }

            val response = authService.resetPassword(header, ResetPasswordRequest(newPassword))

            if (response.isSuccessful) {
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event("Password updated"),
                        changeSucceed = true
                    )
                }
            }

        }
    }

}