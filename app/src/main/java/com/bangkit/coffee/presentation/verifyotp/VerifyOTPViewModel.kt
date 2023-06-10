package com.bangkit.coffee.presentation.verifyotp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.source.remote.AuthService
import com.bangkit.coffee.data.source.remote.model.VerifyCodeUser
import com.bangkit.coffee.shared.wrapper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authService: AuthService
) : ViewModel() {

    private val email = checkNotNull<String>(savedStateHandle["email"])

    private val _stateFlow = MutableStateFlow(VerifyOTPState(email=email))
    val stateFlow = _stateFlow.asStateFlow()

    fun verifyOTP(code: String){
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }

            val response = authService.verifyCode(VerifyCodeUser(email, code))

            if (response.isSuccessful) {
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event("Code verified"),
                        token = Event(response.body()?.token),
                        verified = true
                    )
                }

            }

            if (response.code() == 401) {
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event("Wrong code. Please re-check and try again")
                    )
                }
            }
        }
    }

}