package com.example.pizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pizza.ui.screens.*
import com.example.pizza.ui.theme.PizzaTheme
import com.example.pizza.viewmodel.PizzaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val pizzaViewModel: PizzaViewModel = viewModel()
                    
                    NavHost(
                        navController = navController,
                        startDestination = "menu"
                    ) {
                        composable("menu") {
                            MenuScreen(
                                navController = navController,
                                viewModel = pizzaViewModel
                            )
                        }
                        composable("cart") {
                            CartScreen(
                                navController = navController,
                                viewModel = pizzaViewModel
                            )
                        }
                        composable("checkout") {
                            CheckoutScreen(
                                navController = navController,
                                viewModel = pizzaViewModel
                            )
                        }
                        composable("order_confirmation/{orderId}") { backStackEntry ->
                            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                            OrderConfirmationScreen(
                                navController = navController,
                                orderId = orderId
                            )
                        }
                    }
                }
            }
        }
    }
}