package com.bangkit.coffee.presentation.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.welcome.components.DividerWithText
import com.bangkit.coffee.presentation.welcome.components.PageIndicator
import com.bangkit.coffee.shared.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    state: WelcomeState = WelcomeState(),
    actions: WelcomeActions = WelcomeActions(),
) {
    val pagerState = rememberPagerState()
    val pageCount = state.carouselItems.size
    val isLoaded = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App title
        Row(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
            )
        }

        // App features
        Spacer(modifier = Modifier.weight(1f))
        HorizontalPager(
            pageCount = pageCount,
            state = pagerState,
        ) { i ->
            LaunchedEffect(Unit) { isLoaded.value = true }

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = state.carouselItems[i].image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(1f),
                )
                Text(
                    text = stringResource(state.carouselItems[i].title),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(state.carouselItems[i].description),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (isLoaded.value) {
            PageIndicator(
                numberOfPages = pageCount,
                selectedPage = pagerState.currentPage,
                defaultRadius = 10.dp,
                selectedLength = 30.dp,
                space = 10.dp,
                animationDurationInMillis = 500,
            )
        } else {
            Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(modifier = Modifier.weight(1f))

        // Login button
        Button(
            onClick = actions.navigateToSignIn,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .testTag("SignInButton")
        ) {
            Text(text = stringResource(R.string.sign_in_action))
        }

        DividerWithText(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            text = stringResource(R.string.or)
        )

        // Register button
        OutlinedButton(
            onClick = actions.navigateToSignUp,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .testTag("SignUpButton")
        ) {
            Text(text = stringResource(R.string.sign_up_action))
        }
    }
}

@Composable
@Preview(name = "Welcome", showBackground = true)
private fun WelcomeScreenPreview() {
    AppTheme {
        WelcomeScreen(
            state = WelcomeState(
                carouselItems = listOf(
                    WelcomeCarouselItem(
                        image = R.drawable.welcome_1,
                        title = R.string.welcome_title_1,
                        description = R.string.welcome_description_1
                    ),
                )
            )
        )
    }
}

