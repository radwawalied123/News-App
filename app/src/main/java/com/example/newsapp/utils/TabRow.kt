package com.example.newsapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.model.Constants
import com.example.newsapp.model.api.SourcesItem
import com.example.newsapp.model.api.SourcesResponse
import com.example.newsapp.ui.theme.green
import com.example.newsapp.api.ApiManager
import com.example.newsapp.fragments.news.NewsViewModel


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsSourcesTabRow(
    vm: NewsViewModel = viewModel(),
    category: String,
    onTabSelected: (sourceId: String) -> Unit
) {


    LaunchedEffect(Unit) {
        vm.getSources(category)
    }
    if (vm.sourcesList.isNotEmpty()) {
        LaunchedEffect(Unit) {
            val sourceId = vm.sourcesList.get(0)?.id
            onTabSelected(sourceId ?: "")
        }
    }
    ScrollableTabRow(
        selectedTabIndex = vm.selectedTabIndex.intValue,
        edgePadding = 8.dp,
        indicator = {},
        divider = {}) {
        vm.sourcesList.forEachIndexed { index, item ->
            Tab(
                selected = index == vm.selectedTabIndex.intValue,
                onClick = {
                    onTabSelected(item?.id ?: "")
                    vm.selectedTabIndex.intValue = index
                },
                selectedContentColor = Color.White,
                unselectedContentColor = green
            ) {
                Text(
                    text = item?.name ?: "", modifier = if (vm.selectedTabIndex.intValue == index)
                        Modifier
                            .padding(8.dp)
                            .background(green, RoundedCornerShape(50))
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    else
                        Modifier
                            .padding(8.dp)
                            .border(2.dp, green, CircleShape)
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }
    }
}