package com.bangkit.coffee.presentation.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.presentation.BaseViewModel
import com.bangkit.coffee.presentation.signin.components.SignInForm
import com.bangkit.coffee.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SignInViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<SignInState> = MutableStateFlow(SignInState.Idle())

    val stateFlow: StateFlow<SignInState> = _stateFlow.asStateFlow()

    fun setPasswordVisibility(visibility: Boolean) {
        viewModelScope.launch {
            _stateFlow.update {
                when (it) {
                    is SignInState.Idle -> it.copy(passwordVisible = visibility)
                    is SignInState.Error -> SignInState.Idle(passwordVisible = visibility)
                    else -> it
                }
            }
        }
    }

    fun signIn(formData: SignInForm) {
        viewModelScope.launch {
            _stateFlow.update { SignInState.InProgress }
            delay(1000)
            if (Random.nextBoolean()) {
                _stateFlow.update { SignInState.Error(message = Event("Something went wrong")) }
            } else {
                _stateFlow.update { SignInState.SignedIn }
            }
        }
    }

}