package com.bangkit.coffee.presentation.diseasedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.shared.theme.AppTheme
import com.mxalbert.sharedelements.SharedElement
import com.wajahatiqbal.blurhash.BlurHashPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetailScreen(
    state: DiseaseDetailState = DiseaseDetailState(),
    actions: DiseaseDetailActions = DiseaseDetailActions()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.disease_detail)) },
                navigationIcon = {
                    IconButton(onClick = actions.navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_up)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
        ) {
            Box {
                SharedElement(
                    key = state.disease.cacheKey,
                    screenKey = "disease-detail"
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(state.disease.imageURL)
                            .crossfade(true)
                            .diskCacheKey(state.disease.cacheKey)
                            .memoryCacheKey(state.disease.cacheKey)
                            .build(),
                        contentDescription = stringResource(R.string.coffee_leaf_image),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.no_image),
                        placeholder = BlurHashPainter(
                            blurHash = state.disease.blurHash,
                            width = 800,
                            height = 800,
                            scale = 0.1f,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Badge(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Text(
                        text = stringResource(R.string.sample_disease_on_the_leaf),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = state.disease.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = state.disease.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.how_to_control),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            state.disease.controls.forEach { control ->
                Row {
                    Icon(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 12.dp, top = 6.dp)
                            .size(8.dp)
                    )
                    Text(
                        text = control,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "DiseaseDetail")
private fun DiseaseDetailScreenPreview() {
    AppTheme {
        DiseaseDetailScreen(
            state = DiseaseDetailState(
                disease = DiseaseDummy
            )
        )
    }
}

