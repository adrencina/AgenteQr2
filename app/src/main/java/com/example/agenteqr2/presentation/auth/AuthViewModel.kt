package com.example.agenteqr2.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agenteqr2.core.UIState
import com.example.agenteqr2.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _authState = MutableStateFlow<UIState<String>>(UIState.Loading)
    val authState: StateFlow<UIState<String>> = _authState

    fun exchangeAuthCodeForToken(authCode: String) {
        viewModelScope.launch {
            _authState.value = UIState.Loading
            try {
                val token = authRepository.exchangeAuthCodeForToken(authCode)
                _authState.value = UIState.Success(token)
            } catch (e: Exception) {
                _authState.value = UIState.Error("Error al obtener el token: ${e.message}")
            }
        }
    }
}