package com.bangkit.coffee.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.coffee.R
import com.bangkit.coffee.shared.theme.AppTheme

@Composable
fun SplashScreen(
    state: SplashState = SplashState(),
    actions: SplashActions = SplashActions()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = R.mipmap.ic_launcher,
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(192.dp)
                .align(Alignment.Center)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
        )
    }
}

@Composable
@Preview(name = "Splash", showBackground = true)
private fun SplashScreenPreview() {
    AppTheme {
        SplashScreen()
    }
}

