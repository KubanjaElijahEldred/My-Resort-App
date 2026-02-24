package com.example.myrestaurant.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class AuthRepository(private val context: Context) {
    private val auth: FirebaseAuth = Firebase.auth
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    init {
        auth.addAuthStateListener { firebaseUser ->
            firebaseUser?.let {
                _authState.value = AuthState(
                    currentUser = User(
                        uid = it.uid,
                        email = it.email ?: "",
                        displayName = it.displayName ?: "",
                        photoUrl = it.photoUrl?.toString(),
                        emailVerified = it.isEmailVerified,
                        createdAt = it.metadata?.creationTimestamp ?: System.currentTimeMillis()
                    ),
                    isEmailVerified = it.isEmailVerified,
                    isLoading = false
                )
            } ?: run {
                _authState.value = AuthState(isLoading = false)
            }
        }
    }

    suspend fun signInWithEmail(email: String, password: String): ValidationResult {
        return try {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            val result = auth.signInWithEmailAndPassword(email, password).await()
            
            result.user?.let {
                if (!it.isEmailVerified) {
                    sendEmailVerification()
                    ValidationResult(
                        isValid = false,
                        errorMessage = "Please verify your email. Check your inbox for verification link."
                    )
                } else {
                    ValidationResult(isValid = true)
                }
            } ?: ValidationResult(
                isValid = false,
                errorMessage = "Login failed. Please check your credentials."
            )
        } catch (e: Exception) {
            _authState.value = _authState.value.copy(isLoading = false, error = e.message)
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Login failed"
            )
        } finally {
            _authState.value = _authState.value.copy(isLoading = false)
        }
    }

    suspend fun registerWithEmail(
        email: String,
        password: String,
        displayName: String
    ): ValidationResult {
        return try {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            
            result.user?.let { user ->
                val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()
                
                user.updateProfile(profileUpdates).await()
                sendEmailVerification()
                
                ValidationResult(
                    isValid = true,
                    errorMessage = "Registration successful! Please check your email to verify your account."
                )
            } ?: ValidationResult(
                isValid = false,
                errorMessage = "Registration failed"
            )
        } catch (e: Exception) {
            _authState.value = _authState.value.copy(isLoading = false, error = e.message)
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Registration failed"
            )
        } finally {
            _authState.value = _authState.value.copy(isLoading = false)
        }
    }

    suspend fun signInWithGoogle(activity: Activity): ValidationResult {
        return try {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(com.google.android.gms.base.R.string.default_web_client_id))
                .requestEmail()
                .build()
            
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            val signInIntent = googleSignInClient.signInIntent
            activity.startActivityForResult(signInIntent, 1001)
            
            // This will be handled in the activity result
            ValidationResult(isValid = true)
        } catch (e: Exception) {
            _authState.value = _authState.value.copy(isLoading = false, error = e.message)
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Google sign-in failed"
            )
        }
    }

    suspend fun handleGoogleSignInResult(data: Any?): ValidationResult {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task?.let {
                val account = it.result
                account?.let { googleAccount ->
                    val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
                    val result = auth.signInWithCredential(credential).await()
                    
                    result.user?.let {
                        ValidationResult(isValid = true)
                    } ?: ValidationResult(
                        isValid = false,
                        errorMessage = "Failed to authenticate with Google"
                    )
                } ?: ValidationResult(
                    isValid = false,
                    errorMessage = "Google sign-in cancelled"
                )
            } ?: ValidationResult(
                isValid = false,
                errorMessage = "Google sign-in failed"
            )
        } catch (e: Exception) {
            _authState.value = _authState.value.copy(isLoading = false, error = e.message)
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Google sign-in failed"
            )
        }
    }

    suspend fun sendPasswordResetEmail(email: String): ValidationResult {
        return try {
            auth.sendPasswordResetEmail(email).await()
            ValidationResult(
                isValid = true,
                errorMessage = "Password reset email sent. Check your inbox."
            )
        } catch (e: Exception) {
            ValidationResult(
                isValid = false,
                errorMessage = e.message ?: "Failed to send reset email"
            )
        }
    }

    private suspend fun sendEmailVerification() {
        try {
            auth.currentUser?.sendEmailVerification()?.await()
        } catch (e: Exception) {
            Log.e("AuthRepository", "Failed to send email verification", e)
        }
    }

    suspend fun reloadUser() {
        try {
            auth.currentUser?.reload()?.await()
        } catch (e: Exception) {
            Log.e("AuthRepository", "Failed to reload user", e)
        }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState()
    }

    fun getCurrentUser(): User? {
        return auth.currentUser?.let {
            User(
                uid = it.uid,
                email = it.email ?: "",
                displayName = it.displayName ?: "",
                photoUrl = it.photoUrl?.toString(),
                emailVerified = it.isEmailVerified,
                createdAt = it.metadata?.creationTimestamp ?: System.currentTimeMillis()
            )
        }
    }
}
