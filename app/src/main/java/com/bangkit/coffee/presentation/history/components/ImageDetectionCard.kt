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
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.shared.theme.AppTheme
import com.bangkit.coffee.shared.util.toTimeString
import com.wajahatiqbal.blurhash.BlurHashPainter
import java.util.Date
import java.util.UUID

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
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardDefaults.shape)
                .aspectRatio(1.3f),
            model = ImageRequest.Builder(context)
                .data(imageDetection.imageUrl)
                .crossfade(true)
                .diskCacheKey("image-detection-${imageDetection.id}")
                .memoryCacheKey("image-detection-${imageDetection.id}")
                .build(),
            contentDescription = stringResource(R.string.coffee_leaf_image),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.no_image),
            placeholder = BlurHashPainter(
                blurHash = "LEHC4WWB2yk8pyoJadR*.7kCMdnj",
                width = 290,
                height = 200,
                scale = 0.1f,
            )
        )
        Text(
            text = imageDetection.result.orEmpty(),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        )
        Text(
            text = imageDetection.detectedAt.toTimeString(),
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
            imageDetection = ImageDetection(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "https://picsum.photos/300",
                "Cercospora Leaf Spot",
                Date(),
                Date()
            )
        )
    }
}