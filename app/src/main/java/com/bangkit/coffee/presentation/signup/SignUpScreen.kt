package com.bangkit.coffee.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.signup.components.SignUpForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    state: SignUpState = SignUpState(),
    actions: SignUpActions = SignUpActions()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = actions.navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_up)
                )
            }
        })

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .aspectRatio(1.35f),
            painter = painterResource(R.drawable.sign_up),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        ProvideSignUpActions(actions = actions) {
            SignUpForm()
        }
    }
}

@Composable
@Preview(name = "SignUp", showBackground = true)
private fun SignUpScreenPreview() {
    SignUpScreen()
}

