package com.starscoffee.terminal.presentation

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QRDataState (
    val orderNumber: Int = 0,
    val orderItems: List<Foods> = emptyList(),
    val paymentMethod: PaymentChannels = PaymentChannels(0, "", 0),
    val vouchersUsed: List<Voucher> = emptyList(),
    val totalCost: String = ""
)

data class Voucher (
    val id: Int,
    val pointsRequired: Int,
    @SerializedName("voucherName")
    var voucherName: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("value")
    var value: Int,
    @SerializedName("image_url")
    var image_url: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("restaurant")
    var restaurant: Int = -1,
    var isAvailable: Int = 1
) : Serializable


data class Foods(
    val id: Int,
    @SerializedName("foodName")
    var foodName: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("image_url")
    var image_url: String,
    var isFavorite: Int = 0,
    var isAvailable: Int = 1,
    var quantity: Int = 1
) : Serializable

class PaymentChannels(id: Int, title: String, icon: Int) {
    var pName: String = title
}


data class MainScreenState(
    val details:String = "Start scanning to get details"
)
