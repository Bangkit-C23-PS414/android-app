package com.bangkit.coffee.presentation.signin.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.signin.LocalSignInActions
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    isPasswordVisible: Boolean
) {
    val actions = LocalSignInActions.current
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.padding(24.dp)) {
        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(24.dp))

        form(SignInFormState::class) {
            field(SignInFormState::email) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.value?.value.orEmpty(),
                    onValueChange = this::setField,
                    isError = resultState.value is FieldResult.Error,
                    label = {
                        Text(text = stringResource(R.string.email_address))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.email_hint))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            field(SignInFormState::password) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.value?.value.orEmpty(),
                    onValueChange = this::setField,
                    isError = resultState.value is FieldResult.Error,
                    label = {
                        Text(text = stringResource(R.string.password))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.password_hint))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                    },
                    visualTransformation = if (isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconToggleButton(
                            checked = isPasswordVisible,
                            onCheckedChange = actions.setPasswordVisibility,
                        ) {
                            val iconRes = if (isPasswordVisible) {
                                R.drawable.baseline_visibility_24
                            } else {
                                R.drawable.baseline_visibility_off_24
                            }

                            Icon(
                                painter = painterResource(iconRes),
                                contentDescription = null,
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = actions.navigateToForgotPassword,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(R.string.forgot_password_question))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = actions.signIn,
                modifier = Modifier.fillMaxWidth(),
                enabled = this.formState.value is FormResult.Success
            ) {
                Text(text = stringResource(R.string.sign_in))
            }
        }
    }
}

@Preview(name = "SignInForm")
@Composable
private fun PreviewSignInForm() {
    SignInForm(
        isPasswordVisible = false
    )
}