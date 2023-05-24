package com.bangkit.coffee.presentation.imagedetectiondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.ImageDetectionDummy
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.util.toDateTimeString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSickFragment(
    modifier: Modifier = Modifier,
    imageDetection: ImageDetection,
    disease: Disease
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageDetection.imageUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = stringResource(R.string.coffee_leaf_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Badge(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = stringResource(R.string.accuracy_format, 88),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.we_found_a_disease),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = disease.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = stringResource(
                    R.string.detected_at_format,
                    imageDetection.detectedAt.toDateTimeString()
                ),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            text = disease.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.TipsAndUpdates,
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.how_to_control),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        disease.controls.forEach { control ->
            Row(
                Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .size(8.dp)
                )
                Text(
                    text = control,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(name = "DetailSickFragment")
@Composable
private fun PreviewDetailSickFragment() {
    DetailSickFragment(
        imageDetection = ImageDetectionDummy,
        disease = DiseaseDummy
    )
}