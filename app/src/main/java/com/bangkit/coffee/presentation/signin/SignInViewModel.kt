package com.bangkit.coffee.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.presentation.signin.components.SignInForm
import com.bangkit.coffee.shared.wrapper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow = MutableStateFlow(SignInState())
    val stateFlow = _stateFlow.asStateFlow()

    fun setPasswordVisibility(visibility: Boolean) {
        _stateFlow.update {
            it.copy(passwordVisible = visibility)
        }
    }

    fun signIn(formData: SignInForm) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(inProgress = true) }
            delay(1000)
            if (Random.nextBoolean()) {
                _stateFlow.update {
                    it.copy(
                        inProgress = false,
                        message = Event("Something went wrong")
                    )
                }
            } else {
                _stateFlow.update {
                    it.copy(
                        inProgress = false,
                        signedIn = true
                    )
                }
            }
        }
    }

}