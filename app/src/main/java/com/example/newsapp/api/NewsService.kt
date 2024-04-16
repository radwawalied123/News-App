package com.example.newsapp.api

import com.example.newsapp.model.api.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    //to get the sources
    @GET("top-headlines/sources")
    fun getNewsSource(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
        //Call supposed to have the parameters you get back from the server
    ): Call<SourcesResponse>

    //to get the news from the sources
    @GET("everything")
    fun getNewsFromSource(
        @Query("apiKey") apiKey: String,
        @Query("sources") sourceId: String
    ): Call<ArticlesResponse>

    @GET("everything")
    fun getNewsForDetails(
        @Query("q") title: String,
        @Query("searchIn") topic: String = "title",
        @Query("apiKey") apiKey: String

    ): Call<ArticlesResponse>

}