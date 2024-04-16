package com.example.newsapp.fragments

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.NewsActivity
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import com.example.newsapp.ui.theme.gray2
import com.example.newsapp.ui.theme.green
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.NewsTopAppBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsScreen(title: String, titleResId: Int) {
    var newsItem by remember {
        mutableStateOf<ArticlesItem?>(null)
    }

    var getNewsItemCall: Call<ArticlesResponse>? = null
    LaunchedEffect(key1 = Unit) {
        getNewsItemCall = ApiManager.getNewsServices()
            .getNewsForDetails(title = title, apiKey = Constants.API_KEY)
        getNewsItemCall?.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    val news = response.body()?.articles?.get(0)
                    newsItem = news
                }
            }

            override fun onFailure(p0: Call<ArticlesResponse>, p1: Throwable) {
                Log.e("getNewsItemCall onFailure", p1.localizedMessage ?: "")
            }


        })
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            getNewsItemCall?.cancel()
        }
    }

    newsItem?.let { NewsDetailsContent(it) }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsDetailsScreenPreview() {
    NewsDetailsScreen("", R.string.news_app)
}

@Composable
fun NewsDetailsContent(newsitem: ArticlesItem) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .paint(
                painterResource(R.drawable.pattern),
                contentScale = ContentScale.Crop
            )
            .verticalScroll(rememberScrollState())
    ) {
        NewsCard(model = newsitem)
        NewsDetailsCard(newsitem)
    }
}

@Composable
fun NewsDetailsCard(newsitem: ArticlesItem) {
    val context = (LocalContext.current) as NewsActivity
    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp)
    )
    {
        Text(text = newsitem.content ?: "", modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.fillMaxWidth(0.7f))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                context.openWebsitesForNews(newsitem.url ?: "")
            }) {
            Spacer(modifier = Modifier.fillMaxWidth(0.5f))
            Text(
                text = stringResource(R.string.view_full_article),
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = gray2
            )
            Image(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = "Right arrow to view full article"
            )
        }
    }
}
