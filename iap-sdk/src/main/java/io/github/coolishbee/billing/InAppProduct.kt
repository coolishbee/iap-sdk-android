package io.github.coolishbee.billing

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InAppProduct(
    val productId: String,
    val currency: String,
    val price: String,
    val title: String,
    val decs: String
): Parcelable
