package com.akash.mybarcodescanner.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {

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
            Text(text = "Order Items: ${state.value.orderItems.joinToString { it.foodName }}")
            Text(text = "Payment Method: ${state.value.paymentMethod.pName}")
            Text(text = "Vouchers Used: ${state.value.vouchersUsed.joinToString { it.voucherName }}")
            Text(text = "Total Cost: ${state.value.totalCost}")
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.5f), contentAlignment = Alignment.BottomCenter) {
            Button(onClick = { viewModel.startScanning() }) {
                Text(text = "start scanning")
            }
        }
    }
}