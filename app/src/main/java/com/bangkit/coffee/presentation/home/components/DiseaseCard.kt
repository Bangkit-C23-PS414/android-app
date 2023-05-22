package com.bangkit.coffee.presentation.home.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
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
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(1.3f),
            model = ImageRequest.Builder(context)
                .data(disease.imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = disease.name,
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

@Preview(name = "DiseaseCard")
@Composable
private fun PreviewDiseaseCard() {
    AppTheme {
        DiseaseCard(
            disease = Disease(
                "zDlhByJLobGnwZuJKBJ",
                "Cercospora Leaf Spot",
                "Cercospora coffeicola",
                "This fungal disease occurs in coffee plantations that lack proper nutrient balance. It spreads through wind and rain splash and thrives in humid and warm environments. The symptoms can be observed in newly generated leaves and tissue, appearing as brown spots that start at the edges of the coffee leaves and spread toward the center. The disease can also be seen on the branches, starting at the spots where leaves have fallen.",
                listOf(
                    "Keep a balance and controlled fertilization plan, add organic matter to your soil, and balance the shadow& lighting of your plantation.",
                    "Fungicides that contain copper and triazoles are effective in combating this disease."
                ),
                "https://picsum.photos/300"
            )
        )
    }
}