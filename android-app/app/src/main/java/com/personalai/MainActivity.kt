package com.personalai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private val networkExecutor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AssistantHome(
                    onCheckBackend = { onResult ->
                        networkExecutor.execute {
                            val result = BackendApi.checkHealth()
                            runOnUiThread { onResult(result) }
                        }
                    },
                )
            }
        }
    }

    override fun onDestroy() {
        networkExecutor.shutdownNow()
        super.onDestroy()
    }
}

@Composable
private fun AssistantHome(onCheckBackend: ((String) -> Unit) -> Unit) {
    var isChecking by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("Tap below to check the backend.") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("MTech Personal AI", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Day 1 · Android client and API health check")
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                enabled = !isChecking,
                onClick = {
                    isChecking = true
                    message = "Checking backend…"
                    onCheckBackend { result ->
                        message = result
                        isChecking = false
                    }
                },
            ) {
                Text(if (isChecking) "Checking…" else "Check backend")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(message)
        }
    }
}
