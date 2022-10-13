package io.github.coolishbee.billing

import android.app.Activity
import io.github.coolishbee.UniversalCallback

interface BillingInterface {

    fun init(productList: List<String>,
             callback: UniversalCallback<List<InAppProduct>>
    )

    fun queryProductDetailsAsync(productList: List<String>,
                                 callback: UniversalCallback<List<InAppProduct>>
    )

    fun restorePurchases()

    fun purchase(activity: Activity,
                 productId: String,
                 callback: UniversalCallback<PurchaseResult>
    )
}