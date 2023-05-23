package com.bangkit.coffee.presentation.camera.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.ui.theme.AppTheme

@Composable
fun FocusRing(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Outlined.Circle,
        contentDescription = null,
        modifier = modifier.size(48.dp),
        tint = Color.White
    )
}

@Preview(name = "FocusRing")
@Composable
private fun PreviewFocusRing() {
    AppTheme {
        FocusRing()
    }
}