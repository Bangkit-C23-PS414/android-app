package com.bangkit.coffee.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.source.remote.AuthService
import com.bangkit.coffee.data.source.remote.model.ForgotPasswordUser
import com.bangkit.coffee.shared.wrapper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(ForgotPasswordState())
    val stateFlow = _stateFlow.asStateFlow()

    fun forgotPassword(email: String){
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }

            val response = authService.forgotPassword(ForgotPasswordUser(email))

            if (response.isSuccessful) {
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event("Verification code was sent to $email"),
                        emailInput = email
                    )
                }
            }

            if (response.code() == 404){
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event("$email is not registered")
                    )
                }
            }
        }
    }

}