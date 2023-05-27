package com.bangkit.coffee.presentation.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.presentation.BaseViewModel
import com.bangkit.coffee.presentation.signin.components.SignInForm
import com.bangkit.coffee.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SignInViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _eventFlow = Channel<SignInEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

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
                _stateFlow.update { it.copy(inProgress = false) }
                _eventFlow.send(SignInEvent.ShowToast(Event("Something went wrong")))
            } else {
                _eventFlow.send(SignInEvent.SignedIn)
            }
        }
    }

}