package com.example.routepiresfront.data.model.auth

/**
 * DTO de login alinhado ao backend (/login).
 */
data class LoginRequest(
    val email: String,
    val senha: String
)
