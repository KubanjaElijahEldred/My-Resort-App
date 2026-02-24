package com.example.myrestaurant.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrestaurant.resort.DeepOlive
import com.example.myrestaurant.resort.LuxuryBeige
import com.example.myrestaurant.resort.DarkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onGoogleSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var registerError by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    
    val context = LocalContext.current
    val authState = SimpleAuthManager.authState

    LaunchedEffect(authState.value.currentUser) {
        if (authState.value.currentUser != null) {
            if (authState.value.currentUser!!.emailVerified) {
                onRegisterSuccess()
            } else {
                // Account created but not verified - show message and verify button
                successMessage = "Account created! Click the button below to verify your email and access the app."
            }
        }
    }

    fun validateInputs(): Boolean {
        return when {
            displayName.isBlank() -> {
                registerError = "Please enter your name"
                false
            }
            email.isBlank() -> {
                registerError = "Please enter your email"
                false
            }
            !email.contains("@") -> {
                registerError = "Please enter a valid email address"
                false
            }
            password.length < 6 -> {
                registerError = "Password must be at least 6 characters"
                false
            }
            password != confirmPassword -> {
                registerError = "Passwords do not match"
                false
            }
            else -> {
                registerError = null
                true
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepOlive)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo/Title
        Text(
            text = "My Resort",
            color = LuxuryBeige,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Account",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Display Name Field
                OutlinedTextField(
                    value = displayName,
                    onValueChange = { 
                        displayName = it
                        registerError = null
                        successMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Full Name", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Name", tint = Color.Gray)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LuxuryBeige,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = LuxuryBeige,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = LuxuryBeige,
                        unfocusedLabelColor = Color.Gray
                    ),
                    isError = registerError != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        registerError = null
                        successMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email", tint = Color.Gray)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LuxuryBeige,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = LuxuryBeige,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = LuxuryBeige,
                        unfocusedLabelColor = Color.Gray
                    ),
                    isError = registerError != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        registerError = null
                        successMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password", tint = Color.Gray)
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = Color.Gray
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) androidx.compose.ui.text.input.VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LuxuryBeige,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = LuxuryBeige,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = LuxuryBeige,
                        unfocusedLabelColor = Color.Gray
                    ),
                    isError = registerError != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { 
                        confirmPassword = it
                        registerError = null
                        successMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Confirm Password", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Confirm Password", tint = Color.Gray)
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                tint = Color.Gray
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) androidx.compose.ui.text.input.VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LuxuryBeige,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = LuxuryBeige,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = LuxuryBeige,
                        unfocusedLabelColor = Color.Gray
                    ),
                    isError = registerError != null
                )

                // Error/Success Message
                registerError?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                successMessage?.let { message ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
                        color = Color.Green,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Verify Email Button (appears immediately after registration)
                    if (authState.value.currentUser != null && !authState.value.currentUser!!.emailVerified) {
                        Button(
                            onClick = {
                                val currentUser = authState.value.currentUser!!
                                val result = SimpleAuthManager.verifyEmail(currentUser.email, "instant-verify")
                                if (result.isValid) {
                                    onRegisterSuccess() // Navigate to home
                                } else {
                                    registerError = result.errorMessage
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                            enabled = !authState.value.isLoading
                        ) {
                            if (authState.value.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("Verify Email & Access App", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Register Button
                Button(
                    onClick = {
                        if (validateInputs()) {
                            val result = SimpleAuthManager.register(email, password, displayName, context)
                            if (result.isValid) {
                                successMessage = result.errorMessage
                            } else {
                                registerError = result.errorMessage
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = LuxuryBeige),
                    enabled = !authState.value.isLoading && 
                             displayName.isNotBlank() && 
                             email.isNotBlank() && 
                             password.isNotBlank() && 
                             confirmPassword.isNotBlank()
                ) {
                    if (authState.value.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = DarkText,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Create Account", color = DarkText, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Divider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                    Text(
                        text = "OR",
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 12.sp
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Google Sign-In Button
                OutlinedButton(
                    onClick = onGoogleSignIn,
                    modifier = Modifier.fillMaxWidth(),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryBeige)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Google",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Continue with Google",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sign In Link
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Already have an account? ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    TextButton(
                        onClick = onNavigateToLogin,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            "Sign In",
                            color = LuxuryBeige,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
