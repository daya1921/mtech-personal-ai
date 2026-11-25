package com.personalai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
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
                    onSendMessage = { message, onResult ->
                        networkExecutor.execute {
                            val result = BackendApi.sendMessage(message)
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
private fun AssistantHome(
    onCheckBackend: ((String) -> Unit) -> Unit,
    onSendMessage: (String, (String) -> Unit) -> Unit,
) {
    var isBusy by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Check the backend or send a text message.") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("MTech Personal AI", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Android client · API connection")
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                enabled = !isBusy,
                onClick = {
                    isBusy = true
                    status = "Checking backend…"
                    onCheckBackend { result ->
                        status = result
                        isBusy = false
                    }
                },
            ) {
                Text(if (isBusy) "Working…" else "Check backend")
            }
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isBusy,
                label = { Text("Message") },
                placeholder = { Text("Type a short message") },
                maxLines = 4,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                enabled = !isBusy && input.isNotBlank(),
                onClick = {
                    val request = input.trim()
                    isBusy = true
                    status = "Sending text to backend…"
                    onSendMessage(request) { result ->
                        status = result
                        isBusy = false
                    }
                },
            ) {
                Text("Send to backend")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(status, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Text requests use a development placeholder until a model provider is connected.",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
