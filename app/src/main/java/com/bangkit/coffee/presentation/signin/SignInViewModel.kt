package com.bangkit.coffee.presentation.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.repository.UserPreferencesRepository
import com.bangkit.coffee.data.source.remote.AuthService
import com.bangkit.coffee.data.source.remote.RemoteUtil
import com.bangkit.coffee.data.source.remote.model.LoginUser
import com.bangkit.coffee.presentation.signin.components.SignInForm
import com.bangkit.coffee.shared.wrapper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authService: AuthService,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

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

            val (email, password) = formData
            val loginUser = LoginUser(email, password)

            try {
                val response = authService.login(loginUser)
                _stateFlow.update {
                    it.copy(
                        inProgress = false,
                        message = Event("Signed in")
                    )
                }
                userPreferencesRepository.updateToken(response.token)
                _stateFlow.update {
                    it.copy(
                        signedIn = true
                    )
                }
                Timber.d("logged in with token " + response.token)
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorMessage = RemoteUtil.extractErrorMessageFromJson(errorBody)
                    _stateFlow.update {
                        it.copy(
                            inProgress = false,
                            message = Event(errorMessage)
                        )
                    }
                }
            } catch (e: Exception) {
                Timber.d(e.toString())
            }
        }
    }

}