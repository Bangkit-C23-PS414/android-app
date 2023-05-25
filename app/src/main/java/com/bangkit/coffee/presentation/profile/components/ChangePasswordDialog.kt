package com.bangkit.coffee.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.profile.LocalProfileActions
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordDialog(
    modifier: Modifier = Modifier
) {
    val actions = LocalProfileActions.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = actions.closeChangePassword,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(modifier = modifier) {
            form(ChangePasswordForm::class) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = stringResource(R.string.change_password))
                        },
                        navigationIcon = {
                            IconButton(onClick = actions.closeChangePassword) {
                                Icon(Icons.Filled.Close, contentDescription = null)
                            }
                        },
                    )

                    field(ChangePasswordForm::oldPassword) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .focusRequester(focusRequester),
                            value = this.state.value?.value.orEmpty(),
                            onValueChange = this::setField,
                            isError = resultState.value is FieldResult.Error,
                            supportingText = {
                                if (resultState.value is FieldResult.Error) {
                                    Text(text = stringResource(R.string.old_password_error))
                                }
                            },
                            label = {
                                Text(text = stringResource(R.string.old_password))
                            },
                            placeholder = {
                                Text(text = stringResource(R.string.old_password_hint))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = stringResource(R.string.password)
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next,
                            ),
                        )
                    }

                    field(ChangePasswordForm::newPassword) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = this.state.value?.value.orEmpty(),
                            onValueChange = this::setField,
                            isError = resultState.value is FieldResult.Error,
                            supportingText = {
                                if (resultState.value is FieldResult.Error) {
                                    Text(text = stringResource(R.string.new_password_error))
                                }
                            },
                            label = {
                                Text(text = stringResource(R.string.new_password))
                            },
                            placeholder = {
                                Text(text = stringResource(R.string.new_password_hint))
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Filled.Lock, contentDescription = stringResource(R.string.new_password))
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next,
                            ),
                        )
                    }

                    field(ChangePasswordForm::confirmNewPassword) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = this.state.value?.value.orEmpty(),
                            onValueChange = this::setField,
                            isError = resultState.value is FieldResult.Error,
                            supportingText = {
                                if (resultState.value is FieldResult.Error) {
                                    Text(text = stringResource(R.string.confirm_new_password_error))
                                }
                            },
                            label = {
                                Text(text = stringResource(R.string.confirm_new_password))
                            },
                            placeholder = {
                                Text(text = stringResource(R.string.confirm_new_password_hint))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = stringResource(R.string.confirm_new_password)
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done,
                            ),
                        )
                    }

                    Button(
                        onClick = actions.closeChangePassword,
                        enabled = formState.value is FormResult.Success,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = stringResource(R.string.update_password))
                    }
                }
            }
        }
    }
}

@Preview(name = "ChangePasswordDialog", showBackground = true)
@Composable
private fun PreviewChangePasswordDialog() {
    ChangePasswordDialog()
}