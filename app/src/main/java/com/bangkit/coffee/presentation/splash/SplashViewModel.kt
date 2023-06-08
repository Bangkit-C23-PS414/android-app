package com.bangkit.coffee.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    sessionRepository: SessionRepository
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<SplashState> = MutableStateFlow(SplashState())
    val stateFlow: StateFlow<SplashState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.flow().collect { token ->
                _stateFlow.update {
                    it.copy(authenticated = !token.isNullOrBlank())
                }
            }
        }
    }
}