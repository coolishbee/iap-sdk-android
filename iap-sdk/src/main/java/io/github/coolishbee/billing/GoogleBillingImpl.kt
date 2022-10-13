package io.github.coolishbee.billing

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.*
import io.github.coolishbee.UniversalCallback

class GoogleBillingImpl(
    activity: Activity
) : BillingInterface, PurchasesUpdatedListener {

    private val skuDetailsList: MutableList<SkuDetails> = arrayListOf()
    private lateinit var purchaseCallback: UniversalCallback<PurchaseResult>

    private var billingClient = BillingClient.newBuilder(activity)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    override fun init(productList: List<String>,
                      callback: UniversalCallback<List<InAppProduct>>
    )
    {
        Log.d(TAG, "init")
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if(billingResult.responseCode == BillingClient.BillingResponseCode.OK)
                {
                    Log.d(TAG, "init OK")
                    //val strList = listOf("boxer_1000", "boxer_2000")
                    queryProductDetailsAsync(productList, callback)
                }else{
                    Log.d(TAG, billingResult.toString())
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d(TAG, "onBillingServiceDisconnected")
            }

        })
    }

    override fun queryProductDetailsAsync(productList: List<String>,
                                          callback: UniversalCallback<List<InAppProduct>>
    )
    {
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(productList).setType(BillingClient.SkuType.INAPP)

        Log.d(TAG, "querySkuDetailsAsync")
        billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsResult ->
            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK
                && skuDetailsResult != null) {
                val iapList = ArrayList<InAppProduct>()
                for (skuDetail in skuDetailsResult)
                {
                    //Log.d(TAG, skuDetail.toString())
                    skuDetailsList.add(skuDetail)
                    iapList.add(
                        InAppProduct(
                            skuDetail.sku,
                            skuDetail.priceCurrencyCode,
                            skuDetail.price,
                            skuDetail.title,
                            skuDetail.description)
                    )
                }
                callback.success?.invoke(iapList)
            }else{
                Log.d(TAG, billingResult.toString())
            }
        }
    }

    override fun purchase(activity: Activity,
                          productId: String,
                          callback: UniversalCallback<PurchaseResult>
    )
    {
        this.purchaseCallback = callback

        val sku = getSkuDetail(productId)
        if(sku != null){
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(sku)
                .build()
            val responseCode = billingClient.launchBillingFlow(activity, flowParams).responseCode
        }else{
            // sku null
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult,
                                    list: MutableList<Purchase>?)
    {
        if(billingResult.responseCode == BillingClient.BillingResponseCode.OK
            && list != null) {
            for (purchase in list){
                consumeAsync(purchase)
            }
        }else{
            Log.d(TAG, billingResult.toString())
        }
    }

    private fun consumeAsync(purchase: Purchase) {

        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        billingClient.consumeAsync(consumeParams) { billingResult, purchaseToken ->
            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){

                Log.d(TAG, purchase.originalJson)
                Log.d(TAG, purchaseToken)

                val purchaseResult = PurchaseResult(
                    purchase.orderId,
                    purchase.packageName,
                    purchase.skus[0],
                    purchase.purchaseTime,
                    purchaseToken,
                    purchase.originalJson
                )
                purchaseCallback.success?.invoke(purchaseResult)
            }else{
                Log.d(TAG, billingResult.toString())
                Log.d(TAG, purchaseToken)
            }
        }
    }

    override fun restorePurchases() {

    }

    private fun getSkuDetail(pid: String) : SkuDetails? {
        for (item in skuDetailsList) {
            if (item.sku == pid) {
                return item
            }
        }
        return null
    }

    companion object {
        private const val TAG = "GoogleBillingImpl"
    }
}