package com.iap.sdktest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iap.sdktest.databinding.FragmentApiBinding
import io.github.coolishbee.UniversalCallback
import io.github.coolishbee.api.IAPApiClient
import io.github.coolishbee.api.IAPApiClientBuilder
import io.github.coolishbee.billing.InAppProduct
import io.github.coolishbee.billing.PurchaseResult

class IAPApisFragment : Fragment() {
    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    private lateinit var apiClient: IAPApiClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiClient = activity?.let { IAPApiClientBuilder(it).build() }!!

        binding.initBillingBtn.setOnClickListener {
            val strList = listOf("boxer_1000", "boxer_2000")
            apiClient.initBilling(strList,
                UniversalCallback<List<InAppProduct>>().apply {
                    success = { res ->
                        addLog(res.toString())
                    }
                    failure = { res ->
                        addLog(res.toString())
                    }
                })
        }

        binding.inappPurchaseBtn1.setOnClickListener {
            addLog("purchase")
            //apiClient.purchase(requireActivity(), "boxer_1000")
            apiClient.purchase(requireActivity(), "boxer_1000",
                UniversalCallback<PurchaseResult>().apply {
                    success = { res ->
                        addLog(res.toString())
                    }
                    failure = { res ->
                        addLog(res.toString())
                    }
                }
            )
        }

        binding.clearLogBtn.setOnClickListener {
            binding.log.text = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addLog(logText: String) {
        binding.log.text = logText.plus("\n" + binding.log.text)

        Log.d(TAG, "log: $logText")
        //println(logText)
    }

    companion object {
        private const val TAG = "IAPApisFragment"
    }
}