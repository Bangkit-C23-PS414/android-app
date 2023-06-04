package com.bangkit.coffee.presentation.signup

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.source.remote.AuthService
import com.bangkit.coffee.data.source.remote.RemoteUtil
import com.bangkit.coffee.data.source.remote.model.RegisterUser
import com.bangkit.coffee.presentation.signup.components.SignUpForm
import com.bangkit.coffee.shared.wrapper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authService: AuthService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(SignUpState())
    val stateFlow = _stateFlow.asStateFlow()

    fun signUp(form: SignUpForm) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateFlow.update { it.copy(loading = true) }

            val (name, email, password, confirmPassword) = form
            val registerUser = RegisterUser(name, email, password, confirmPassword)

            try {
                val response = authService.register(registerUser)
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event("Signed up with email: ${response.email}")
                    )
                }
            } catch (e: HttpException) {
                if (e.code() == 400) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorMessage = RemoteUtil.extractErrorMessageFromJson(errorBody)
                    _stateFlow.update {
                        it.copy(
                            loading = false,
                            message = Event(errorMessage)
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("SignUpViewModel", e.toString())
            }
        }
    }

}