package com.example.newsapp.fragments.news

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import com.example.newsapp.model.api.SourcesItem
import com.example.newsapp.model.api.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel() : ViewModel() {
    val newsListStates = mutableStateListOf<ArticlesItem?>()
    val selectedTabIndex = mutableIntStateOf(0)

    val sourcesList = mutableStateListOf<SourcesItem?>()
    fun getSources(category: String) {
        ApiManager
            .getNewsServices()
            .getNewsSource(Constants.API_KEY, category)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    val sources = response.body()?.sources
                    if (sources?.isNotEmpty() == true) {
                        sourcesList.addAll(sources)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })
    }

    fun getNews(sourceId: String) {
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
}