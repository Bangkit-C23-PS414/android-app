package com.bangkit.coffee.presentation.resetpassword

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow = MutableStateFlow(ResetPasswordState())
    val stateFlow = _stateFlow.asStateFlow()

}