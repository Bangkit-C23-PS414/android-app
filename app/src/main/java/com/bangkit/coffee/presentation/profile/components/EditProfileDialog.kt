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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.profile.LocalProfileActions
import com.bangkit.coffee.presentation.profile.ProfileActions
import com.bangkit.coffee.presentation.profile.ProvideProfileActions
import com.bangkit.coffee.ui.theme.AppTheme
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    modifier: Modifier = Modifier,
    name: String = ""
) {
    val actions = LocalProfileActions.current

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
                            IconButton(onClick = actions.closeEditProfile) {
                                Icon(Icons.Filled.Close, contentDescription = null)
                            }
                        },
                    )

                    field(EditProfileForm::name) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = this.state.value?.value ?: name,
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
                                imeAction = ImeAction.Done,
                            ),
                        )
                    }

                    Button(
                        onClick = actions.closeEditProfile,
                        enabled = formState.value is FormResult.Success,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
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