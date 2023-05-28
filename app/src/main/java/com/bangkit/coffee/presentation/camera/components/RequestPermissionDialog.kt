package com.bangkit.coffee.presentation.camera.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.R
import com.bangkit.coffee.shared.theme.AppTheme

@Composable
fun RequestPermissionDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.request_camera_title))
        },
        text = {
            Text(text = stringResource(R.string.request_camera_description))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.grant_access))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.deny))
            }
        }
    )
}

@Preview(name = "RequestPermissionDialog", showBackground = true)
@Composable
private fun PreviewRequestPermissionDialog() {
    AppTheme {
        RequestPermissionDialog()
    }
}