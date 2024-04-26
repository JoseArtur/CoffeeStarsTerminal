package com.starscoffee.terminal.domain.repo

import com.starscoffee.terminal.presentation.QRDataState
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import kotlinx.coroutines.flow.Flow



interface MainRepo {

    fun startScanning(): Flow<String?>
    fun transformQRCodeData(json: ByteArray): QRDataState {
        println("json: $json")
        val jsonToStr = String(json, Charsets.UTF_8)
        println("jsonToStr: $jsonToStr")
        return try {
            val qrDataState = Gson().fromJson(jsonToStr, QRDataState::class.java)
            println("jsonToStr: $qrDataState")
            qrDataState
        } catch (e: Exception) {
            println("Error parsing JSON: ${e.message}")
            QRDataState()
        }
    }
}
