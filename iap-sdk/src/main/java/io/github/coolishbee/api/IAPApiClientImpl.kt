package io.github.coolishbee.api

import android.app.Activity
import io.github.coolishbee.UniversalCallback
import io.github.coolishbee.billing.GoogleBillingImpl
import io.github.coolishbee.billing.InAppProduct
import io.github.coolishbee.billing.PurchaseResult

class IAPApiClientImpl(
    activity: Activity
) : IAPApiClient {

    private val billingManager = GoogleBillingImpl(activity)

    override fun initBilling(productList: List<String>,
                             callback: UniversalCallback<List<InAppProduct>>
    )
    {
        val strList = listOf("boxer_1000", "boxer_2000")
        billingManager.init(strList, callback)
    }

    override fun restorePurchases() {
        TODO("Not yet implemented")
    }

    override fun purchase(activity: Activity,
                          productId: String,
                          callback: UniversalCallback<PurchaseResult>
    )
    {
        billingManager.purchase(activity, productId, callback)
    }
}