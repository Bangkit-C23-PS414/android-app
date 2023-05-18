package com.bangkit.coffee.presentation.signin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.signin.components.SignInForm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SignInScreen(
    state: SignInState = SignInState(),
    actions: SignInActions = SignInActions()
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val bringButtonIntoView = {
        coroutineScope.launch {
            delay(1)
            bringIntoViewRequester.bringIntoView()
        }
    }

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .onSizeChanged { bringButtonIntoView() }
    ) {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = actions.navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_up)
                )
            }
        })

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(R.drawable.sign_in),
            contentDescription = null
        )

        ProvideSignInActions(actions = actions) {
            SignInForm(
                modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
                isPasswordVisible = state.isPasswordVisible,
            )
        }
    }
}

@Composable
@Preview(name = "SignIn", showBackground = true)
private fun SignInScreenPreview() {
    SignInScreen()
}

