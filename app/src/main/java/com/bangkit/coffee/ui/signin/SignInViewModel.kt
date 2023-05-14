package com.bangkit.coffee.ui.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.ui.BaseViewModel
import com.bangkit.coffee.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())

    val stateFlow: StateFlow<SignInState> = _stateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = SignInState(),
    )

    fun update() {
        _stateFlow.update {
            it.copy(isLoading = true)
        }
    }

}