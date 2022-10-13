package io.github.coolishbee.api

import android.app.Activity
import io.github.coolishbee.UniversalCallback
import io.github.coolishbee.billing.InAppProduct
import io.github.coolishbee.billing.PurchaseResult

interface IAPApiClient {

    fun initBilling(productList: List<String>,
                    callback: UniversalCallback<List<InAppProduct>>)

    fun restorePurchases()

    fun purchase(activity: Activity,
                 productId: String,
                 callback: UniversalCallback<PurchaseResult>
    )
}