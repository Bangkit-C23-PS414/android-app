package com.bangkit.coffee.presentation.history.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.ImageDetectionDummy
import com.bangkit.coffee.domain.entity.DetectionResult
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.shared.const.LABEL_HEALTHY
import com.bangkit.coffee.shared.theme.AppTheme
import com.bangkit.coffee.shared.util.toTimeString
import com.wajahatiqbal.blurhash.BlurHashPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetectionCard(
    modifier: Modifier = Modifier,
    imageDetection: ImageDetection,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (!imageDetection.isDetected) {
                MaterialTheme.colorScheme.outlineVariant
            } else if (imageDetection.label == LABEL_HEALTHY) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardDefaults.shape)
                .aspectRatio(1.3f),
            model = ImageRequest.Builder(context)
                .data(imageDetection.fileURL)
                .crossfade(true)
                .diskCacheKey(imageDetection.cacheKey)
                .memoryCacheKey(imageDetection.cacheKey)
                .build(),
            contentDescription = stringResource(R.string.coffee_leaf_image),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.no_image),
            placeholder = BlurHashPainter(
                blurHash = imageDetection.blurHash,
                width = 224,
                height = 224,
                scale = 0.1f,
            )
        )

        Text(
            text = if (imageDetection.isDetected) {
                stringResource(DetectionResult.getName(imageDetection.label))
            } else {
                stringResource(R.string.still_processing)
            },
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        )
        Text(
            text = if (imageDetection.isDetected) {
                imageDetection.detectedAt.toTimeString()
            } else {
                stringResource(R.string.please_wait)
            },
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
        )
    }
}

@Preview(name = "ImageDetectionCard", showBackground = true, widthDp = 300)
@Composable
private fun PreviewImageDetectionCard() {
    AppTheme {
        ImageDetectionCard(
            imageDetection = ImageDetectionDummy
        )
    }
}