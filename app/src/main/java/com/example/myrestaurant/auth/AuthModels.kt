package com.example.myrestaurant.auth

data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String? = null,
    val emailVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class AuthState(
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val error: String? = null,
    val isEmailVerified: Boolean = false
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val displayName: String
)

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
