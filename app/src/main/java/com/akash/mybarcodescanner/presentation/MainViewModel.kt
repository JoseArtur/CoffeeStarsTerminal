package com.akash.mybarcodescanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.mybarcodescanner.domain.repo.MainRepo
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


    fun startScanning(){
        viewModelScope.launch {
             repo.startScanning().collect{
                if (!it.isNullOrBlank()){
                    val qrData = repo.transformQRCodeData(it)
                    _state.value = state.value.copy(
                        orderItems = qrData.orderItems,
                        paymentMethod = qrData.paymentMethod,
                        vouchersUsed = qrData.vouchersUsed,
                        totalCost = qrData.totalCost
                    )
                }
            }
        }
    }
}