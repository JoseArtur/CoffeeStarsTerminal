package com.starscoffee.terminal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starscoffee.terminal.domain.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepo
):ViewModel() {

    private val _state = MutableStateFlow(QRDataState())
    val state = _state.asStateFlow()
    private var orderNumber = 0

    fun validateOrder() {
        // Implement your order validation logic here

        // If the order is valid, increment the order number and update the state
        orderNumber++
        _state.value = state.value.copy(orderNumber = orderNumber)
    }
    fun startScanning(){
        viewModelScope.launch {
             repo.startScanning().collect{
                if (!it.isNullOrBlank()){
                    val qrData = repo.transformQRCodeData(it.toByteArray())
                    _state.value = state.value.copy(
                        orderItems = qrData.orderItems,
                        paymentMethod = qrData.paymentMethod,
                        vouchersUsed = qrData.vouchersUsed,
                        totalCost = qrData.totalCost)
                }
            }
        }
    }
}