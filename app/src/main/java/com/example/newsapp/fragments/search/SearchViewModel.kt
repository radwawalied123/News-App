package com.example.newsapp.fragments.search

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private var getSearchedArticlesCall: Call<ArticlesResponse>? = null
    var newsList by mutableStateOf(listOf<ArticlesItem?>())
    var newsFoundState by mutableStateOf(true)
    var searchQuery by mutableStateOf("")
    var isFocused by mutableStateOf(false)
    val focusRequester = FocusRequester()
    var loadingState by mutableStateOf(false)
    var messageState by mutableStateOf<String?>(null)
    fun getNewsFor(searchQuery: String) {
        getSearchedArticlesCall = ApiManager.getNewsServices()
            .getSearchedArticles(searchQuery = "search", Constants.API_KEY)
        getSearchedArticlesCall?.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {

                if (response.isSuccessful) {
                    val news = response.body()?.articles
                    if (!news.isNullOrEmpty()) {
                        newsList = news
                        newsFoundState = true
                    } else {
                        newsFoundState = false
                    }
                } else {
                    messageState = response.errorBody().toString()

                }
                loadingState = false
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                loadingState = false
                messageState = t.localizedMessage ?: ""

            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        getSearchedArticlesCall?.cancel()
    }
}