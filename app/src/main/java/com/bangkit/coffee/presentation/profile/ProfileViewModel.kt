package com.bangkit.coffee.presentation.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        Timber.d("Profile init")
    }

    private val _stateFlow: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())

    val stateFlow: StateFlow<ProfileState> = _stateFlow.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        Timber.d("Profile onCleared")
    }
}