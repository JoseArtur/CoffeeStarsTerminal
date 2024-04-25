package com.starscoffee.terminal.domain.repo

import com.starscoffee.terminal.presentation.QRDataState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow


interface MainRepo {

    fun startScanning(): Flow<String?>
    fun transformQRCodeData(json: ByteArray): QRDataState {
        println("json: $json")
        val jsonToStr = String(json, Charsets.UTF_8)
        println("jsonToStr: $jsonToStr")
        println("jsonToStr: ${Gson().fromJson(jsonToStr, QRDataState::class.java)}")
        return Gson().fromJson(jsonToStr, QRDataState::class.java)
    }
}