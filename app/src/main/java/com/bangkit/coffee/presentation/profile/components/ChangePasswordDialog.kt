package com.bangkit.coffee.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
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
import com.bangkit.coffee.shared.theme.AppTheme
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordDialog(
    modifier: Modifier = Modifier,
    inProgress: Boolean = false
) {
    val actions = LocalProfileActions.current
    val focusRequester = remember { FocusRequester() }

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
                            IconButton(
                                onClick = actions.closeChangePassword,
                                modifier = Modifier.testTag("CloseButton")
                            ) {
                                Icon(Icons.Filled.Close, contentDescription = null)
                            }
                        },
                    )

                    field(ChangePasswordForm::oldPassword) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .focusRequester(focusRequester)
                                .testTag("OldPasswordField"),
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
                                Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next,
                            ),
                        )

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    }

                    field(ChangePasswordForm::newPassword) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .testTag("NewPasswordField"),
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
                                Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
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
                                .padding(horizontal = 16.dp)
                                .testTag("ConfirmNewPasswordField"),
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
                                Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done,
                            ),
                        )
                    }

                    val formData = formState.value
                    Button(
                        onClick = {
                            if (formData is FormResult.Success && !inProgress) {
                                actions.changePassword(formData.data)
                            }
                        },
                        enabled = formState.value is FormResult.Success,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .testTag("SaveButton")
                    ) {
                        if (inProgress) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(15.dp)
                            )
                        }

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
    AppTheme {
        ChangePasswordDialog()
    }
}