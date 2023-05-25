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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseCard(
    modifier: Modifier = Modifier,
    disease: Disease
) {
    val context = LocalContext.current

    OutlinedCard(
        onClick = {},
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardDefaults.shape)
                .aspectRatio(1.3f),
            model = ImageRequest.Builder(context)
                .data(disease.imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = stringResource(R.string.image),
            contentScale = ContentScale.FillBounds
        )
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