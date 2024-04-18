package com.example.newsapp.fragments.newsDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.NewsActivity
import com.example.newsapp.R
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.ui.theme.gray2
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsScreen(
    vm: NewsDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    title: String, scope: CoroutineScope,
    drawerState: DrawerState,
    titleResId: Int
) {

    if (vm.newsItem == null) {
        LaunchedEffect(key1 = Unit) {
            vm.getNewsItem(title)
        }
    }



    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = false,
            shouldDisplayMenuIcon = false,
            titleResId = R.string.news, scope = scope,
            drawerState = drawerState,
        )



    }) { paddingValues: PaddingValues ->
        vm.newsItem?.let { NewsDetailsContent(it,paddingValues) }
    }

}



@Composable
fun NewsDetailsContent(newsitem: ArticlesItem, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues.calculateTopPadding())
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
