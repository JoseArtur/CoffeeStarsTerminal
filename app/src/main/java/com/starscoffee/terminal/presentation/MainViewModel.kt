package com.starscoffee.terminal.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starscoffee.terminal.domain.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepo
):ViewModel() {

    private val _state = MutableStateFlow(QRDataState())
    val state = _state.asStateFlow()
    private var orderNumber = 0
    private val client = OkHttpClient()
     fun callRequest(request: Request): String? {
        val client = OkHttpClient()
        return try {
            val response: Response = client.newCall(request).execute()
            println(response)
            if (response.isSuccessful) {
                response.body()?.string()
            } else {
                println("Unexpected response code: ${response.code()}")
                null
            }
        } catch (e: IOException) {
            println("Exception during network call: ${e.message}")
            null
        }
    }
    fun validateOrder(): Boolean {
        // Implement your order validation logic here
        if(state.value.json !=null )
        {
            val url = "http://172.24.155.55:8090/coffee-shop/validate-order"
            val requestBody = RequestBody.create(MediaType.parse("application/json"), state.value.json)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build()
            var foodsString = ""
            val foodsDeferred = GlobalScope.async(Dispatchers.IO) {
                callRequest(request)
            }

            runBlocking {
                // Wait for the result and access the list of vouchers
                val foods = foodsDeferred.await()
                foodsString = foods.toString()
                // Process the list of vouchers as needed
                println("Foods: $foods")
            }
            println("FoodList: $foodsString")
            if (foodsString != "" )
            {
                // If the order is valid, increment the order number and update the state
                orderNumber++
                _state.value = state.value.copy(orderNumber = orderNumber)
                return true;
            }
        }
        return false;

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
                        totalCost = qrData.totalCost,
                        userEmail = qrData.userEmail,
                        json =  String(it.toByteArray(), Charsets.UTF_8))
                }
            }
        }
    }
}