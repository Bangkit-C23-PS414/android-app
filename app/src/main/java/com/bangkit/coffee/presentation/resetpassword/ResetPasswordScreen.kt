package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.resetpassword.components.ResetPasswordForm
import com.bangkit.coffee.shared.const.STATIC_URL
import com.bangkit.coffee.shared.theme.AppTheme
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@Composable
fun ResetPasswordScreen(
    state: ResetPasswordState = ResetPasswordState(),
    actions: ResetPasswordActions = ResetPasswordActions()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .aspectRatio(1.4f),
            model = ImageRequest.Builder(context)
                .decoderFactory(SvgDecoder.Factory())
                .data(STATIC_URL + "reset_password.svg")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.reset_password),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.reset_password_description),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        form(ResetPasswordForm::class) {
            field(ResetPasswordForm::password) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
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

            field(ResetPasswordForm::confirmPassword) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
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

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = actions.navigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ResetPasswordButton"),
                enabled = this.formState.value is FormResult.Success
            ) {
                Text(text = stringResource(R.string.reset_password))
            }
        }
    }
}

@Composable
@Preview(name = "ResetPassword", showBackground = true)
private fun ResetPasswordScreenPreview() {
    AppTheme {
        ResetPasswordScreen()
    }
}

