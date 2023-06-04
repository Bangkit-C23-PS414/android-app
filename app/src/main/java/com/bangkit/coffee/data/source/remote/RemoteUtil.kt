package com.bangkit.coffee.data.source.remote

import org.json.JSONException
import org.json.JSONObject

class RemoteUtil {
    companion object{
        fun extractErrorMessageFromJson(errorBody: String?): String {
            return try {
                val json = JSONObject(errorBody)
                json.getString("error")
            } catch (e: JSONException) {
                "Unknown error"
            }
        }
    }
}