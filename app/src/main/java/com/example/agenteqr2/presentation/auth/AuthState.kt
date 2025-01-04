package com.example.agenteqr2.presentation.auth

sealed class AuthState {
    data object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
