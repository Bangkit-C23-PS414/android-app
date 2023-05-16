package com.bangkit.coffee.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.bangkit.coffee.di.IODispatcher
import com.bangkit.coffee.util.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    private val _isValid = MutableStateFlow<Boolean>(false)
    val isValid = _isValid.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            tokenManager.getToken().collect { token ->
                _token.emit(token)
                _isValid.emit(tryParseJWT(token)?.isExpired(0) == false)
            }
        }
    }

    private fun tryParseJWT(token: String?): JWT? {
        return try {
            if (token.isNullOrBlank()) throw Exception("No token")
            JWT(token)
        } catch (e: Exception) {
            return null
        }
    }
}