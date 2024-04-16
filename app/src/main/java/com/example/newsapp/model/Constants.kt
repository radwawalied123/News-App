package com.example.newsapp.model

import com.example.newsapp.R

object Constants {
    val API_KEY: String = "b432c5a05f9c431d83dc84ba8030a980"
    val categories = listOf(
        Category(
            "sports", R.color.redCat, R.drawable.sports, R.string.sports

        ),
        Category(
            "politics", R.color.blueCat, R.drawable.politics, R.string.Technology

        ),
        Category(
            "health", R.color.pinkCat, R.drawable.health, R.string.Health

        ),
        Category(
            "business", R.color.brownCat, R.drawable.bussines, R.string.Business

        ), Category(
            "enviroment", R.color.lilacCat, R.drawable.environment, R.string.General

        ),
        Category(
            "science", R.color.yellowCat, R.drawable.science, R.string.Science

        )
    )
}