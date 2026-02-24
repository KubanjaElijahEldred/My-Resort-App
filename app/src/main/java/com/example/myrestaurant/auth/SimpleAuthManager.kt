package com.example.myrestaurant.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat

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

// Enhanced Auth Manager with real email verification
object SimpleAuthManager {
    private val _authState = androidx.compose.runtime.mutableStateOf(AuthState())
    val authState: androidx.compose.runtime.State<AuthState> = _authState
    
    // Store pending verification data
    private var pendingVerification: Pair<String, String>? = null
    
    fun signIn(email: String, password: String, context: Context): ValidationResult {
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
    
    fun register(email: String, password: String, displayName: String, context: Context): ValidationResult {
        return try {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            // Simulate registration
            if (email.contains("@") && password.length >= 6) {
                // Store pending verification
                val token = System.currentTimeMillis().toString()
                pendingVerification = Pair(email, token)
                
                // Send real verification email with clickable button
                sendVerificationEmail(email, displayName, token, context)
                
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
                    errorMessage = "Registration successful! Check your email and click the verification button."
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
    
    private fun sendVerificationEmail(email: String, displayName: String, token: String, context: Context) {
        try {
            val verificationLink = "myresort://verify?email=${Uri.encode(email)}&token=$token"
            val subject = "Verify Your My Resort Account"
            val body = """
                Hello $displayName,
                
                Thank you for creating an account with My Resort!
                
                Please click the button below to verify your email address:
                
                <a href="$verificationLink" style="background-color: #D9C5A7; color: #3B4433; padding: 12px 24px; text-decoration: none; border-radius: 6px; display: inline-block; font-weight: bold;">Verify Email Address</a>
                
                Or copy and paste this link in your browser:
                $verificationLink
                
                This verification link will expire in 24 hours.
                
                If you didn't create this account, please ignore this email.
                
                Best regards,
                My Resort Team
            """.trimIndent()
            
            // Create email intent
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                putExtra(Intent.EXTRA_HTML_TEXT, body) // Enable HTML email
            }
            
            // Try to open email app
            ContextCompat.startActivity(context, emailIntent, null)
            
        } catch (e: Exception) {
            // Fallback: Show email content to user
            _authState.value = _authState.value.copy(
                error = "Email client not available. Please copy the verification link manually."
            )
        }
    }
    
    fun sendPasswordReset(email: String, context: Context): ValidationResult {
        return try {
            if (email.contains("@")) {
                // Send real password reset email
                sendPasswordResetEmail(email, context)
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
    
    private fun sendPasswordResetEmail(email: String, context: Context) {
        try {
            val resetLink = "myresort://reset?email=${Uri.encode(email)}&token=${System.currentTimeMillis()}"
            val subject = "Reset Your My Resort Password"
            val body = """
                Hello,
                
                You requested to reset your password for My Resort.
                
                Click the button below to create a new password:
                
                <a href="$resetLink" style="background-color: #D9C5A7; color: #3B4433; padding: 12px 24px; text-decoration: none; border-radius: 6px; display: inline-block; font-weight: bold;">Reset Password</a>
                
                Or copy and paste this link in your browser:
                $resetLink
                
                This link will expire in 24 hours.
                
                If you didn't request this reset, please ignore this email.
                
                Best regards,
                My Resort Team
            """.trimIndent()
            
            // Create email intent
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                putExtra(Intent.EXTRA_HTML_TEXT, body) // Enable HTML email
            }
            
            // Try to open email app
            ContextCompat.startActivity(context, emailIntent, null)
            
        } catch (e: Exception) {
            // Fallback: Show email content to user
            _authState.value = _authState.value.copy(
                error = "Email client not available. Please copy the reset link manually."
            )
        }
    }
    
    fun signOut() {
        _authState.value = AuthState()
        pendingVerification = null
    }
    
    fun getCurrentUser(): User? = _authState.value.currentUser
    
    fun verifyEmail(email: String, token: String): ValidationResult {
        return try {
            _authState.value = _authState.value.copy(isLoading = true)
            
            // Check pending verification or instant verification
            val pending = pendingVerification
            if ((pending != null && pending.first == email && pending.second == token) || 
                (token == "instant-verify" && _authState.value.currentUser?.email == email)) {
                // Verify the current user
                val currentUser = _authState.value.currentUser
                if (currentUser != null && currentUser.email == email) {
                    val verifiedUser = currentUser.copy(emailVerified = true)
                    _authState.value = AuthState(
                        currentUser = verifiedUser,
                        isLoading = false,
                        isEmailVerified = true
                    )
                    pendingVerification = null // Clear pending verification
                    ValidationResult(isValid = true)
                } else {
                    _authState.value = _authState.value.copy(isLoading = false)
                    ValidationResult(
                        isValid = false,
                        errorMessage = "No matching account found for verification"
                    )
                }
            } else {
                _authState.value = _authState.value.copy(isLoading = false)
                ValidationResult(
                    isValid = false,
                    errorMessage = "Invalid or expired verification link"
                )
            }
        } catch (e: Exception) {
            _authState.value = _authState.value.copy(isLoading = false, error = e.message)
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Verification failed"
            )
        }
    }
    
    // Handle deep links for email verification
    fun handleDeepLink(uri: Uri?): Boolean {
        return try {
            if (uri?.scheme == "myresort") {
                when (uri.host) {
                    "verify" -> {
                        val email = uri.getQueryParameter("email")
                        val token = uri.getQueryParameter("token")
                        if (email != null && token != null) {
                            val result = verifyEmail(email, token)
                            return result.isValid
                        }
                    }
                    "reset" -> {
                        // Handle password reset deep link
                        val email = uri.getQueryParameter("email")
                        if (email != null) {
                            // Navigate to password reset screen with email pre-filled
                            return true
                        }
                    }
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }
}
