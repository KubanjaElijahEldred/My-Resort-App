package com.example.myrestaurant

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myrestaurant.resort.GarugaResortScreen
import com.example.myrestaurant.dashboard.BookingsDashboard
import com.example.myrestaurant.dashboard.RoomManagementDashboard
import com.example.myrestaurant.dashboard.FinancialReportsDashboard
import com.example.myrestaurant.auth.LoginScreen
import com.example.myrestaurant.auth.RegisterScreen
import com.example.myrestaurant.auth.ForgotPasswordScreen
import com.example.myrestaurant.auth.SimpleAuthManager
import com.example.myrestaurant.ui.theme.MyRestaurantTheme

// Using lowercase to follow Kotlin standards and avoid warnings
val deepOlive = Color(0xFF3B4433)
val luxuryBeige = Color(0xFFD9C5A7)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyRestaurantTheme {
                val navController = rememberNavController()

                // Handle deep links for email verification
                val uri = intent.data
                if (uri != null) {
                    SimpleAuthManager.handleDeepLink(uri)
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { 
                                    navController.navigate("home")
                                },
                                onNavigateToRegister = { 
                                    navController.navigate("register")
                                },
                                onNavigateToForgotPassword = { 
                                    navController.navigate("forgot")
                                },
                                onGoogleSignIn = { /* Google Sign-In */ }
                            )
                        }

                        composable("register") {
                            RegisterScreen(
                                onRegisterSuccess = { 
                                    navController.navigate("login")
                                },
                                onNavigateToLogin = { 
                                    navController.popBackStack()
                                },
                                onGoogleSignIn = { /* Google Sign-In */ }
                            )
                        }

                        composable("forgot") {
                            ForgotPasswordScreen(
                                onNavigateToLogin = { 
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("home") {
                            GarugaResortScreen(
                                onNavigateToDashboard = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }

                        composable("bookings") {
                            BookingsDashboard(
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("rooms") {
                            RoomManagementDashboard(
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("revenue") {
                            FinancialReportsDashboard(
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleDashboardScreen(title: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(deepOlive)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, color = luxuryBeige, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = luxuryBeige)
        ) {
            Text("Back to Resort", color = Color(0xFF2D2D2D))
        }
    }
}