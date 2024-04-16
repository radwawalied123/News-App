package com.example.newsapp.model

import com.example.newsapp.R

open class Screen(val route: String, description: Int)

class NewsScreen : Screen("news", R.string.news)
class CategoriesScreen : Screen("categories", R.string.categories)