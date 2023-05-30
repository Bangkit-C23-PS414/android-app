package com.bangkit.coffee.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.shared.const.STATIC_URL
import com.bangkit.coffee.shared.theme.AppTheme

@Composable
fun DisplayErrorFragment(
    modifier: Modifier = Modifier,
    message: String? = null
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .aspectRatio(1.35f),
            model = ImageRequest.Builder(context)
                .decoderFactory(SvgDecoder.Factory())
                .data(STATIC_URL + "sign_in.svg")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = message ?: stringResource(R.string.generic_error_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "DisplayErrorFragment", showBackground = true)
@Composable
private fun PreviewDisplayErrorFragment() {
    AppTheme {
        DisplayErrorFragment()
    }
}