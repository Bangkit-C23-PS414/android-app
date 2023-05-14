package com.bangkit.coffee.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimpleScreen(
    modifier: Modifier = Modifier,
    text: String = "SimpleScreen",
    action: () -> Unit = {},
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = action) {
            Text(text = "Action")
        }
    }
}

@Preview(name = "SimpleScreen", showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun PreviewSimpleScreen() {
    SimpleScreen()
}