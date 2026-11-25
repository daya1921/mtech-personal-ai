package com.personalai

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object BackendApi {
    fun checkHealth(): String {
        val connection = URL("${BuildConfig.API_BASE_URL.trimEnd('/')}/health")
            .openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 3_000
            connection.readTimeout = 3_000

            val responseCode = connection.responseCode
            val stream = if (responseCode in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }
            val body = stream?.bufferedReader()?.use { it.readText() }.orEmpty()

            if (responseCode !in 200..299) {
                "Backend returned HTTP $responseCode"
            } else {
                val response = JSONObject(body)
                "Connected: ${response.getString("service")} v${response.getString("version")}"
            }
        } catch (error: Exception) {
            "Connection failed: ${error.localizedMessage ?: "unknown error"}"
        } finally {
            connection.disconnect()
        }
    }
}
