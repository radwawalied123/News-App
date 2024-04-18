package com.example.newsapp.fragments.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.NewsSourcesTabRow

@Composable
fun NewsScreen(
    vm: NewsViewModel = viewModel(),
    onNewsClick: (String) -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    category: String,
    onSearchClick: () -> Unit,
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
fun NewsList(newsList: List<ArticlesItem?>, onNewsClick: (String) -> Unit) {
    LazyColumn {
        items(newsList.size) { position ->
            newsList[position]?.let {
                NewsCard(model = it) { title ->
                    onNewsClick(title)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsCardPreview() {
}