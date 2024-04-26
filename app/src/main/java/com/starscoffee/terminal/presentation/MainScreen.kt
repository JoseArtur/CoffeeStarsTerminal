package com.starscoffee.terminal.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(),navController: NavController) {

    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //user email
            Text(text = "User Email: ${state.value.userEmail}")
            Text(text = "Order Items: ${state.value.orderItems.joinToString { it.foodName }}")
            Text(text = "Payment Method: ${state.value.paymentMethod.pName}")
            Text(text = "Vouchers Used: ${state.value.vouchersUsed.joinToString { it.name }}")
            Text(text = "Total Cost: ${state.value.totalCost}")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { viewModel.startScanning() }) {
                Text(text = "Start Scanning")
            }
            Button(onClick = { if(viewModel.validateOrder()){

                 navController.navigate("order_screen/${state.value.orderNumber.toString().padStart(10, '0')}")

            }

            }) {
                Text(text = "Validate Order")
            }
        }
    }
}