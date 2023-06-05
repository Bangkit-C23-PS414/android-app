package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.bangkit.coffee.presentation.imagedetectiondetail.components.AccuracyBadge
import com.bangkit.coffee.presentation.imagedetectiondetail.components.DiseaseFoundInfo
import com.bangkit.coffee.presentation.imagedetectiondetail.components.HealthyInfo
import com.bangkit.coffee.presentation.imagedetectiondetail.components.ProcessingInfo
import com.bangkit.coffee.shared.const.DEFAULT_BLUR_HASH
import com.bangkit.coffee.shared.theme.AppTheme
import com.bangkit.coffee.shared.util.toDateTimeString
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.fade
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wajahatiqbal.blurhash.BlurHashPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetectionDetailScreen(
    state: ImageDetectionDetailState = ImageDetectionDetailState(),
    actions: ImageDetectionDetailActions = ImageDetectionDetailActions()
) {
    @Suppress("DEPRECATION")
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.loading)
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.detection_result)) },
                navigationIcon = {
                    IconButton(onClick = actions.navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_up)
                        )
                    }
                },
                actions = {
                    if (state.imageDetection?.isDetected == false && state.waiting) {
                        var progress by remember { mutableStateOf(1f) }
                        val progressAnimate by animateFloatAsState(
                            targetValue = progress,
                            animationSpec = tween(durationMillis = 5000, easing = LinearEasing),
                            label = "countdown"
                        )

                        LaunchedEffect(Unit) { progress = 0f }

                        CircularProgressIndicator(
                            progress = progressAnimate,
                            strokeWidth = 3.dp,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(20.dp)
                        )
                    } else if (state.imageDetection?.isDetected == false && !state.waiting) {
                        CircularProgressIndicator(
                            strokeWidth = 3.dp,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(20.dp)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        @Suppress("DEPRECATION")
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = actions.refresh,
            modifier = Modifier.padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
            ) {
                Box {
                    AsyncImage(
                        model = state.imageDetection?.let {
                            ImageRequest.Builder(context)
                                .data(it.fileURL)
                                .crossfade(true)
                                .diskCacheKey(it.cacheKey)
                                .memoryCacheKey(it.cacheKey)
                                .build()
                        },
                        contentDescription = stringResource(R.string.coffee_leaf_image),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.no_image),
                        placeholder = BlurHashPainter(
                            blurHash = DEFAULT_BLUR_HASH,
                            width = 290,
                            height = 200,
                            scale = 0.1f,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .placeholder(
                                visible = state.imageDetection == null,
                                highlight = PlaceholderHighlight.fade(),
                            )
                    )

                    if (state.imageDetection?.isDetected == true) {
                        AccuracyBadge(
                            accuracy = state.imageDetection.accuracy,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )
                    }
                }

                // Disease info panel
                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .heightIn(min = 75.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .placeholder(
                            visible = state.imageDetection == null,
                            highlight = PlaceholderHighlight.fade(),
                        )
                ) {
                    if (state.imageDetection != null && !state.imageDetection.isDetected) {
                        ProcessingInfo()
                    } else if (state.imageDetection != null && state.disease != null) {
                        DiseaseFoundInfo(diseaseName = state.disease.name,)
                    } else if (state.imageDetection != null) {
                        HealthyInfo()
                    }
                }

                // Detected at
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .placeholder(
                            visible = state.imageDetection?.isDetected != true,
                            highlight = PlaceholderHighlight.fade(),
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = stringResource(
                            R.string.detected_at_format,
                            state.imageDetection?.detectedAt?.toDateTimeString().orEmpty()
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Description section
                Text(
                    text = if (state.imageDetection?.isDetected != true) {
                        stringResource(R.string.lipsum)
                    } else if (state.disease == null) {
                        stringResource(R.string.healthy_explanation)
                    } else {
                        state.disease.description
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .placeholder(
                            visible = state.imageDetection?.isDetected != true,
                            highlight = PlaceholderHighlight.fade(),
                        )
                )

                // How to control section
                if (state.disease != null) {
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
    }
}

@Composable
@Preview(name = "ImageDetectionDetail", showBackground = true)
private fun ImageDetectionDetailScreenPreview() {
    AppTheme {
        ImageDetectionDetailScreen()
    }
}

