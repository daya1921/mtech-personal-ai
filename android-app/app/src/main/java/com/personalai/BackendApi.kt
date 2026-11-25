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

    fun sendMessage(message: String): String {
        val connection = URL(
            "${BuildConfig.API_BASE_URL.trimEnd('/')}/api/v1/assistant/message",
        ).openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "POST"
            connection.connectTimeout = 5_000
            connection.readTimeout = 5_000
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")

            val requestBody = JSONObject().put("message", message).toString()
            connection.outputStream.bufferedWriter(Charsets.UTF_8).use { writer ->
                writer.write(requestBody)
            }

            val responseCode = connection.responseCode
            val stream = if (responseCode in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }
            val body = stream?.bufferedReader()?.use { it.readText() }.orEmpty()
            val response = JSONObject(body)

            if (responseCode !in 200..299) {
                "Backend returned HTTP $responseCode: ${response.optString("detail", "request failed")}"
            } else {
                "${response.getString("reply")} (mode: ${response.getString("mode")})"
            }
        } catch (error: Exception) {
            "Connection failed: ${error.localizedMessage ?: "unknown error"}"
        } finally {
            connection.disconnect()
        }
    }
}
