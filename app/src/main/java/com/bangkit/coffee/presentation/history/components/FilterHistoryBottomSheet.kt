package com.bangkit.coffee.presentation.history.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.entity.DetectionResult
import com.bangkit.coffee.presentation.history.HistoryActions
import com.bangkit.coffee.presentation.history.LocalHistoryActions
import com.bangkit.coffee.presentation.history.ProvideHistoryActions
import com.bangkit.coffee.ui.theme.AppTheme
import com.bangkit.coffee.util.toDateString
import kotlinx.coroutines.launch
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterHistoryBottomSheet(
    modifier: Modifier = Modifier,
    formData: FilterHistoryForm = FilterHistoryForm(),
) {
    val actions = LocalHistoryActions.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var dateRangePickerVisible by remember { mutableStateOf(false) }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = actions.toggleFilter,
        sheetState = sheetState,
    ) {
        form(FilterHistoryForm::class) {
            LaunchedEffect(formData) { submit(formData) }

            val allTimeField = registerField(FilterHistoryForm::allTime)
            val filterDateField = registerField(FilterHistoryForm::dateRange)
            if (dateRangePickerVisible) {
                DateRangePickerDialog(
                    defaultValue = if (allTimeField.value == true) null else filterDateField.value,
                    onDismiss = { dateRangePickerVisible = false },
                    onConfirm = {
                        allTimeField.setField(false)
                        filterDateField.setField(it)
                        dateRangePickerVisible = false

                        /*Timber.d(it.toString())
                        Timber.d(it.startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli().toString())
                        Timber.d(it.endDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli().toString())*/
                    }
                )
            }

            Text(
                text = stringResource(R.string.filter_date),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )

            field(FilterHistoryForm::allTime) {
                // All time
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .selectable(
                            selected = state.value?.value == true,
                            onClick = { setField(true) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp)
                        .testTag("AllTimeSelect"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.value?.value == true,
                        onClick = null
                    )
                    Text(
                        text = stringResource(R.string.all_time),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                // Select date range
                Row(
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp)
                        .clickable { dateRangePickerVisible = true }
                        .padding(horizontal = 16.dp)
                        .testTag("DateRangeSelect"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.value?.value == false,
                        onClick = null
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.select_date_range),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        AnimatedVisibility(
                            visible = state.value?.value == false,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.date_range_format,
                                    filterDateField.value?.startDate?.toDateString().orEmpty(),
                                    filterDateField.value?.endDate?.toDateString().orEmpty()
                                ),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.filter_label),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )

            field(FilterHistoryForm::selectedLabel) {
                DetectionResult.list.forEach { detectionResult ->
                    val formValue = state.value?.value.orEmpty()

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .toggleable(
                                value = formValue.value.contains(detectionResult),
                                onValueChange = { checked ->
                                    setField(formValue.update(checked, detectionResult))
                                },
                                role = Role.Checkbox
                            )
                            .padding(horizontal = 16.dp)
                            .testTag("DiseaseCheckbox")
                    ) {
                        Checkbox(
                            checked = formValue.value.contains(detectionResult),
                            onCheckedChange = null
                        )
                        Text(
                            text = detectionResult.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                val formResult = formState.value

                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            actions.resetFilter()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ClearFilterButton")
                ) {
                    Text(text = stringResource(R.string.reset_filter))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        if (formResult is FormResult.Success) {
                            coroutineScope.launch {
                                sheetState.hide()
                                actions.applyFilter(formResult.data)
                            }
                        }
                    },
                    enabled = formResult is FormResult.Success,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ApplyFilterButton")
                ) {
                    Text(text = stringResource(R.string.apply_filter))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(name = "FilterHistoryBottomSheet", showBackground = true)
@Composable
private fun PreviewFilterHistoryBottomSheet() {
    AppTheme {
        ProvideHistoryActions(actions = HistoryActions()) {
            FilterHistoryBottomSheet()
        }
    }
}