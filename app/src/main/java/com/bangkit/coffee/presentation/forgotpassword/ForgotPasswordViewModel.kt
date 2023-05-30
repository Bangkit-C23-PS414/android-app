package com.bangkit.coffee.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow = MutableStateFlow(ForgotPasswordState())
    val stateFlow = _stateFlow.asStateFlow()

}