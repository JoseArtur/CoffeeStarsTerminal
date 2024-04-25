package com.akash.mybarcodescanner.presentation
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QRDataState (
    val orderItems: List<Foods> = emptyList(),
    val paymentMethod: PaymentChannels = PaymentChannels(0, "", 0),
    val vouchersUsed: List<Voucher> = emptyList(),
    val totalCost: String = ""
)

data class Voucher (
    val id: Int,
    val pointsRequired: Int,
    @SerializedName("name")
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
    var isAvailable: Boolean = true
) : Serializable


data class Foods(
    val id: Int,
    @SerializedName("name")
    var foodName: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("image_url")
    var image_url: String,
    var isFavorite: Boolean = false,
    var isAvailable: Boolean = true,
    var quantity: Int = 1
) : Serializable

class PaymentChannels(id: Int, title: String, icon: Int) {
    var pId: Int = id
    var pName: String = title
    var pIcon: Int = icon
}
