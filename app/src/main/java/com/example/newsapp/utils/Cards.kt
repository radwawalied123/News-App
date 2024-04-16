package com.example.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.R
import com.example.newsapp.api.ArticlesItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(model: ArticlesItem, onNewsClick: ((String) -> Unit)? = null) {
    Card(
        modifier = Modifier
            .clickable {
                if (onNewsClick != null) {
                    onNewsClick(model.title ?: "")
                }
            }
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        GlideImage(
            model = model.urlToImage ?: "",
            contentDescription = stringResource(R.string.image_description),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit,
            loading = placeholder(R.drawable.logo),
        )
        Text(
            text = model.source?.name ?: "",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 10.sp
        )
        Text(
            text = model.title ?: "",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 14.sp, fontWeight = FontWeight.Medium
        )
        Text(
            text = model.publishedAt ?: "",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.End),
            fontSize = 13.sp
        )

    }
}

@Preview
@Composable
fun NewsCardpreview() {
    NewsCard(model = ArticlesItem())
}