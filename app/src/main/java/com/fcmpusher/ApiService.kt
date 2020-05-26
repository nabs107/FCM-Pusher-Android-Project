package com.fcmpusher

import android.util.Log
import com.fcmpusher.constants.APIConstants
import com.fcmpusher.model.FCMResponse
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.Gson

class ApiService {

    fun pushNotification(headers: Map<String, Any>, body: Map<String, Any>, returnValue: (Boolean) -> Unit) {

        Fuel.post(APIConstants.PUSH_FCM)
            .jsonBody(Gson().toJson(body))
            .header(headers)
            .also { Log.d("APIService: ", it.cUrlString()) }
            .responseObject<FCMResponse> { _, _, result ->
                if (result.component1()?.success ?: -1 == 1) {
                    returnValue.invoke(true)
                } else if (result.component1()?.failure ?: -1 == 1) {
                    returnValue.invoke(false)
                } else {
                    returnValue.invoke(false)
                }
            }
    }

}