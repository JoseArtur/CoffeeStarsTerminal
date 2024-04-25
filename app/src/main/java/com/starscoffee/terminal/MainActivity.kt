package com.starscoffee.terminal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starscoffee.terminal.presentation.MainScreen
import com.starscoffee.terminal.presentation.OrderScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main_screen") {
                composable("main_screen") { MainScreen(navController = navController) }
                composable("order_screen/{orderNumber}") { backStackEntry ->
                    OrderScreen(orderNumber = backStackEntry.arguments?.getString("orderNumber"))
                }
            }
        }
    }
}

