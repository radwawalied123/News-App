package com.example.newsapp.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.NewsSourcesTabRow
import com.example.newsapp.utils.NewsTopAppBar

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsFragment(
    onNewsClick: (String) -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    category: String
) {
    val newsListStates = remember {
        mutableStateListOf<ArticlesItem?>()
    }



    Column(modifier = Modifier) {
        NewsSourcesTabRow(category = category) { sourceId ->
            ApiManager
                .getNewsServices()
                .getNewsFromSource(Constants.API_KEY, sourceId)
                .enqueue(object : Callback<ArticlesResponse> {
                    override fun onResponse(
                        call: Call<ArticlesResponse>,
                        response: Response<ArticlesResponse>
                    ) {
                        newsListStates.clear()
                        val newsList = response.body()?.articles
                        if (newsList?.isNotEmpty() == true) {
                            newsListStates.addAll(newsList)
                        }
                    }

                    override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }


                })

        }
        NewsList(newsListStates.filterNotNull()) {
            onNewsClick(it)
        }
    }


}

@Composable
fun NewsList(newsList: List<ArticlesItem>, onNewsClick: (String) -> Unit) {
    LazyColumn {
        items(newsList.size) { position ->
            NewsCard(model = newsList[position]) { title ->
                onNewsClick(title)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsCardPreview() {
}