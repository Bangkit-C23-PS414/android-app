package com.bangkit.coffee.ui.signin

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SignInScreen(
    state: SignInState = SignInState(),
    actions: SignInActions = SignInActions()
) {
    // TODO UI Logic
}

@Composable
@Preview(name = "SignIn")
private fun SignInScreenPreview() {
    SignInScreen()
}

