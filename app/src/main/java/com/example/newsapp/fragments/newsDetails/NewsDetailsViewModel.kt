package com.example.newsapp.fragments.newsDetails

import android.util.Log
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.model.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsViewModel() : ViewModel() {
    var newsItem by mutableStateOf<ArticlesItem?>(null)
    var getNewsItemCall: Call<ArticlesResponse>? = null
    fun getNewsItem(title: String) {
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

    override fun onCleared() {
        super.onCleared()
        getNewsItemCall?.cancel()
    }
}