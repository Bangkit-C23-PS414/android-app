package com.bangkit.coffee.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.signup.components.SignUpForm
import com.bangkit.coffee.presentation.theme.AppTheme
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    state: SignUpState = SignUpState(),
    actions: SignUpActions = SignUpActions()
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = actions.navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_up)
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(contentPadding)
                .padding(24.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .aspectRatio(1.35f),
                painter = painterResource(R.drawable.sign_up),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            form(SignUpForm::class) {
                field(SignUpForm::name) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("FullNameField"),
                        value = this.state.value?.value.orEmpty(),
                        onValueChange = this::setField,
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
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                field(SignUpForm::email) {
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
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                field(SignUpForm::password) {
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
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                field(SignUpForm::confirmPassword) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ConfirmPasswordField"),
                        value = this.state.value?.value.orEmpty(),
                        onValueChange = this::setField,
                        isError = resultState.value is FieldResult.Error,
                        supportingText = {
                            if (resultState.value is FieldResult.Error) {
                                Text(text = stringResource(R.string.confirm_password_error))
                            }
                        },
                        label = {
                            Text(text = stringResource(R.string.confirm_password))
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.confirm_password_hint))
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                field(SignUpForm::termsAndConditionAgreed) {
                    val checked = this.state.value?.value == true
                    val setField = this::setField

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            modifier = Modifier.testTag("AgreementCheckbox"),
                            checked = checked,
                            onCheckedChange = setField,
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.terms_agreement))
                                append(" ")
                                withStyle(style = SpanStyle(Color.Blue)) {
                                    append(stringResource(R.string.terms_and_conditions))
                                }
                                append(".")
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = actions.signUp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("SignUpButton"),
                    enabled = this.formState.value is FormResult.Success
                ) {
                    Text(text = stringResource(R.string.sign_up))
                }
            }
        }
    }
}

@Composable
@Preview(name = "SignUp", showBackground = true)
private fun SignUpScreenPreview() {
    AppTheme {
        SignUpScreen()
    }
}

