package com.bangkit.coffee.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.profile.LocalProfileActions
import com.bangkit.coffee.presentation.profile.ProfileActions
import com.bangkit.coffee.presentation.profile.ProvideProfileActions
import com.bangkit.coffee.shared.theme.AppTheme
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    modifier: Modifier = Modifier,
    name: String = ""
) {
    val actions = LocalProfileActions.current
    val focusRequester = remember { FocusRequester() }

    Dialog(
        onDismissRequest = actions.closeEditProfile,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(modifier = modifier) {
            form(EditProfileForm::class) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = stringResource(R.string.edit_profile))
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = actions.closeEditProfile,
                                modifier = Modifier.testTag("CloseButton")
                            ) {
                                Icon(Icons.Filled.Close, contentDescription = null)
                            }
                        },
                    )

                    field(EditProfileForm::name) {
                        var valueState by remember {
                            mutableStateOf(
                                TextFieldValue(
                                    text = name,
                                    selection = TextRange(name.length)
                                )
                            )
                        }
                        val value = valueState.copy(text = this.state.value?.value ?: name)

                        SideEffect {
                            Timber.d("Side Effect: $value")
                            if (value.selection != valueState.selection ||
                                value.composition != valueState.composition
                            ) {
                                valueState = value
                            }
                        }

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .focusRequester(focusRequester)
                                .testTag("NameField"),
                            value = value,
                            onValueChange = { newValue ->
                                Timber.d("onValueChange: $newValue")
                                valueState = newValue

                                if (value.text != newValue.text) {
                                    this.setField(newValue.text)
                                }
                            },
                            isError = resultState.value is FieldResult.Error,
                            supportingText = {
                                if (resultState.value is FieldResult.Error) {
                                    Text(text = stringResource(R.string.full_name_error))
                                }
                            },
                            label = {
                                Text(text = stringResource(R.string.full_name))
                            },
                            placeholder = {
                                Text(text = stringResource(R.string.full_name_hint))
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
                        )

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    }

                    Button(
                        onClick = actions.closeEditProfile,
                        enabled = formState.value is FormResult.Success,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .testTag("SaveButton")
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Preview(name = "EditProfileDialog", showBackground = true)
@Composable
private fun PreviewEditProfileDialog() {
    AppTheme {
        ProvideProfileActions(actions = ProfileActions()) {
            EditProfileDialog()
        }
    }
}