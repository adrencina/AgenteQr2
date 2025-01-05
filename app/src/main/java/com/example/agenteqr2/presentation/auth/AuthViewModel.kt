package com.example.agenteqr2.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agenteqr2.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    fun authenticate(clientId: String, clientSecret: String) {
        if (clientId.isBlank() || clientSecret.isBlank()) {
            _authState.value = AuthState.Error("Todos los campos son obligatorios.")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val token = authRepository.authenticate(clientId, clientSecret)
                _authState.value = AuthState.Success(token)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error de autenticación: ${e.message}")
            }
        }
    }

    fun exchangeAuthCodeForToken(authCode: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val token = authRepository.exchangeAuthCodeForToken(authCode)
                _authState.value = AuthState.Success(token)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error al obtener el token: ${e.message}")
            }
        }
    }
}