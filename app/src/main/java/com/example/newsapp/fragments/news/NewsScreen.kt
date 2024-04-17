package com.example.newsapp.fragments.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.NewsSourcesTabRow

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsScreen(
    vm: NewsViewModel = viewModel(),
    onNewsClick: (String) -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    category: String
) {


    Column(modifier = Modifier) {
        NewsSourcesTabRow(category = category) { sourceId ->
            vm.getNews(sourceId)

        }
        NewsList(vm.newsListStates.filterNotNull()) {
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