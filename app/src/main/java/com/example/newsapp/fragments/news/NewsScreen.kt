package com.example.newsapp.fragments.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.NewsSourcesTabRow
import com.example.newsapp.utils.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun NewsScreen(
    vm: NewsViewModel = viewModel(), scope: CoroutineScope,
    drawerState: DrawerState,
    onNewsClick: (String) -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    category: String,
    onSearchClick: () -> Unit,
) {
    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = true,
            shouldDisplayMenuIcon = true,
            titleResId = R.string.news_app,  scope = scope,
            drawerState = drawerState,

            onSearchClick = {
                onSearchClick()
            }
        )

    }){paddingValues: PaddingValues ->
        Column(modifier = Modifier.fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .paint(
                painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.Crop
            )) {
            NewsSourcesTabRow(category = category) { sourceId ->
                vm.getNews(sourceId)

            }
            NewsList(vm.newsListStates.filterNotNull()) {
                onNewsClick(it)
            }
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