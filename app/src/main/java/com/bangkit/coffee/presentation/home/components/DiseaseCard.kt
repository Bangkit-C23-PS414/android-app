package com.bangkit.coffee.presentation.home.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.shared.theme.AppTheme
import com.mxalbert.sharedelements.SharedElement
import com.wajahatiqbal.blurhash.BlurHashPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseCard(
    modifier: Modifier = Modifier,
    disease: Disease,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    OutlinedCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        SharedElement(key = disease.cacheKey, screenKey = "home") {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CardDefaults.shape)
                    .aspectRatio(1.3f),
                model = ImageRequest.Builder(context)
                    .data(disease.imageURL)
                    .crossfade(true)
                    .diskCacheKey(disease.cacheKey)
                    .memoryCacheKey(disease.cacheKey)
                    .build(),
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.no_image),
                placeholder = BlurHashPainter(
                    blurHash = disease.blurHash,
                    width = 1,
                    height = 1,
                    scale = 0.1f,
                )
            )
        }
        Text(
            text = disease.name,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(name = "DiseaseCard", showBackground = true)
@Composable
private fun PreviewDiseaseCard() {
    AppTheme {
        DiseaseCard(disease = DiseaseDummy)
    }
}