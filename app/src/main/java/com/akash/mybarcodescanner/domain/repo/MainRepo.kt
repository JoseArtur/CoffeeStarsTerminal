package com.akash.mybarcodescanner.domain.repo

import com.akash.mybarcodescanner.presentation.QRDataState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow


interface MainRepo {

    fun startScanning(): Flow<String?>
    fun transformQRCodeData(json: String): QRDataState
}