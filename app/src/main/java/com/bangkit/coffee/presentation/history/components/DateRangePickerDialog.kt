package com.bangkit.coffee.presentation.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bangkit.coffee.R
import com.bangkit.coffee.ui.theme.AppTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    modifier: Modifier = Modifier,
    defaultValue: FilterDateWrapper? = null,
    onDismiss: () -> Unit = {},
    onConfirm: (FilterDateWrapper) -> Unit = {}
) {
    val state = rememberDateRangePickerState(
        initialSelectedStartDateMillis = defaultValue?.startDate?.time,
        initialSelectedEndDateMillis = defaultValue?.endDate?.time,
    )

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Filled.Close, contentDescription = null)
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = {
                                val startTimestamp = state.selectedStartDateMillis
                                val endTimestamp = state.selectedEndDateMillis

                                if (startTimestamp != null && endTimestamp != null) {
                                    onConfirm(
                                        FilterDateWrapper(
                                            startDate = Date(startTimestamp),
                                            endDate = Date(endTimestamp)
                                        )
                                    )
                                }
                            },
                            enabled = state.selectedEndDateMillis != null
                        ) {
                            Text(text = stringResource(R.string.save))
                        }
                    }
                )

                DateRangePicker(
                    state = state,
                    dateValidator = { timestamp ->
                        val now = Date()
                        Date(timestamp).before(now)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(name = "DateRangePickerDialog")
@Composable
private fun PreviewDateRangePickerDialog() {
    AppTheme {
        DateRangePickerDialog()
    }
}