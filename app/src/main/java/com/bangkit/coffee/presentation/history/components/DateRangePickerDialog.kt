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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bangkit.coffee.R
import com.bangkit.coffee.ui.theme.AppTheme
import com.bangkit.coffee.util.toAdjustedLocalDate
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    modifier: Modifier = Modifier,
    defaultValue: FilterDateWrapper? = null,
    onDismiss: () -> Unit = {},
    onConfirm: (FilterDateWrapper) -> Unit = {}
) {
    val state = rememberDateRangePickerState(
        defaultValue?.startDate?.toInstant(ZoneOffset.UTC)?.toEpochMilli(),
        defaultValue?.endDate?.toInstant(ZoneOffset.UTC)?.toEpochMilli(),
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
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("CloseDialog")
                        ) {
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
                                            startDate = startTimestamp.toAdjustedLocalDate(),
                                            endDate = endTimestamp.toAdjustedLocalDate()
                                                .toLocalDate()
                                                .atTime(LocalTime.MAX)
                                        )
                                    )
                                }
                            },
                            enabled = state.selectedEndDateMillis != null,
                            modifier = Modifier.testTag("SaveButton")
                        ) {
                            Text(text = stringResource(R.string.save))
                        }
                    }
                )

                DateRangePicker(
                    state = state,
                    dateValidator = { epochs ->
                        epochs.toAdjustedLocalDate() <= LocalDate.now().atTime(LocalTime.MAX)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(name = "DateRangePickerDialog", showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun PreviewDateRangePickerDialog() {
    AppTheme {
        DateRangePickerDialog()
    }
}