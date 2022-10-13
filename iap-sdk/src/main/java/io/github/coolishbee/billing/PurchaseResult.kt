package io.github.coolishbee.billing

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PurchaseResult(
    val orderId: String,
    val packageName: String,
    val productId: String,
    val purchaseTime: Long,
    val purchaseToken: String,
    val jsonString: String
): Parcelable