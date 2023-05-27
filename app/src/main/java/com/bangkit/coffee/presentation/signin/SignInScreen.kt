package com.bangkit.coffee.presentation.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.signin.components.SignInForm
import com.bangkit.coffee.ui.theme.AppTheme
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@Composable
fun SignInScreen(
    state: SignInState = SignInState(),
    actions: SignInActions = SignInActions()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .aspectRatio(1.35f),
            model = R.drawable.sign_in,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(24.dp))

        form(SignInForm::class) {
            LaunchedEffect(Unit) {
                submit(
                    SignInForm(
                        email = "myxzlpltk@gmail.com",
                        password = "password"
                    )
                )
            }

            field(SignInForm::email) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("EmailField"),
                    value = this.state.value?.value.orEmpty(),
                    onValueChange = this::setField,
                    isError = resultState.value is FieldResult.Error,
                    supportingText = {
                        if (resultState.value is FieldResult.Error) {
                            Text(text = stringResource(R.string.email_error))
                        }
                    },
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
                )
            }

            field(SignInForm::password) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("PasswordField"),
                    value = this.state.value?.value.orEmpty(),
                    onValueChange = this::setField,
                    isError = resultState.value is FieldResult.Error,
                    supportingText = {
                        if (resultState.value is FieldResult.Error) {
                            Text(text = stringResource(R.string.password_error))
                        }
                    },
                    label = {
                        Text(text = stringResource(R.string.password))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.password_hint))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                    },
                    visualTransformation = if (state.passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconToggleButton(
                            checked = state.passwordVisible,
                            onCheckedChange = actions.setPasswordVisibility,
                        ) {
                            Icon(
                                imageVector = if (state.passwordVisible) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                },
                                contentDescription = stringResource(R.string.toggle_password_visibility),
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
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

            val formData = formState.value
            Button(
                onClick = {
                    if (formData is FormResult.Success && !state.inProgress) {
                        actions.signIn(formData.data)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("SignInButton"),
                enabled = this.formState.value is FormResult.Success
            ) {
                if (state.inProgress) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(15.dp)
                    )
                }

                Text(text = stringResource(R.string.sign_in))
            }
        }
    }
}

@Composable
@Preview(name = "SignIn", showBackground = true)
private fun SignInScreenPreview() {
    AppTheme {
        SignInScreen()
    }
}

