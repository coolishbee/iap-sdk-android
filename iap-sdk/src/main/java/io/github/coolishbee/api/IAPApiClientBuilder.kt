package io.github.coolishbee.api

import android.app.Activity

class IAPApiClientBuilder(
    private val activity: Activity
) {
    private val context = activity.applicationContext

    fun build(): IAPApiClient {
        return IAPApiClientImpl(
            activity
        )
    }


}