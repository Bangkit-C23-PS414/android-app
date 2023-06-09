package com.bangkit.coffee.presentation.verifyotp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.repository.UserPreferencesRepository
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
    private val authService: AuthService,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(VerifyOTPState())
    val stateFlow = _stateFlow.asStateFlow()

    fun verifyOTP(email: String?, code: String){
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }

            val response = authService.verifyCode(VerifyCodeUser(email, code))

            if (response.isSuccessful) {
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event("Code verified"),
                        verified = true
                    )
                }

                val token = response.body()?.token

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