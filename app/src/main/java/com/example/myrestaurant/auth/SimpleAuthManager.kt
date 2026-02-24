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

// Simple Auth Manager without Firebase
object SimpleAuthManager {
    private val _authState = androidx.compose.runtime.mutableStateOf(AuthState())
    val authState: androidx.compose.runtime.State<AuthState> = _authState
    
    fun signIn(email: String, password: String): ValidationResult {
        return try {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            // Simulate authentication
            if (email == "test@myresort.com" && password == "password123") {
                val user = User(
                    uid = "user123",
                    email = email,
                    displayName = "Test User",
                    emailVerified = true
                )
                _authState.value = AuthState(
                    currentUser = user,
                    isLoading = false,
                    isEmailVerified = true
                )
                ValidationResult(isValid = true)
            } else {
                _authState.value = _authState.value.copy(isLoading = false)
                ValidationResult(
                    isValid = false,
                    errorMessage = "Invalid email or password"
                )
            }
        } catch (e: Exception) {
            _authState.value = _authState.value.copy(isLoading = false, error = e.message)
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Login failed"
            )
        }
    }
    
    fun register(email: String, password: String, displayName: String): ValidationResult {
        return try {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            // Simulate registration
            if (email.contains("@") && password.length >= 6) {
                val user = User(
                    uid = "user${System.currentTimeMillis()}",
                    email = email,
                    displayName = displayName,
                    emailVerified = false // Email not verified yet
                )
                _authState.value = AuthState(
                    currentUser = user,
                    isLoading = false,
                    isEmailVerified = false
                )
                ValidationResult(
                    isValid = true,
                    errorMessage = "Registration successful! Please check your email to verify your account."
                )
            } else {
                _authState.value = _authState.value.copy(isLoading = false)
                ValidationResult(
                    isValid = false,
                    errorMessage = if (!email.contains("@")) "Invalid email address" else "Password must be at least 6 characters"
                )
            }
        } catch (e: Exception) {
            _authState.value = _authState.value.copy(isLoading = false, error = e.message)
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Registration failed"
            )
        }
    }
    
    fun sendPasswordReset(email: String): ValidationResult {
        return try {
            if (email.contains("@")) {
                ValidationResult(
                    isValid = true,
                    errorMessage = "Password reset email sent. Check your inbox."
                )
            } else {
                ValidationResult(
                    isValid = false,
                    errorMessage = "Please enter a valid email address"
                )
            }
        } catch (e: Exception) {
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Failed to send reset email"
            )
        }
    }
    
    fun signOut() {
        _authState.value = AuthState()
    }
    
    fun getCurrentUser(): User? = _authState.value.currentUser
}
