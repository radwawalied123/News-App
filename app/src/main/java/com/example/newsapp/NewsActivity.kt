package com.example.newsapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.launch
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.ArticlesItem
import com.example.newsapp.api.ArticlesResponse
import com.example.newsapp.fragments.CategoriesFragment
import com.example.newsapp.fragments.NewsDetailsScreen
import com.example.newsapp.fragments.NewsFragment
import com.example.newsapp.fragments.NewsList
import com.example.newsapp.model.CategoriesScreen
import com.example.newsapp.model.Category
import com.example.newsapp.model.Constants
import com.example.newsapp.model.NewsScreen
import com.example.newsapp.ui.theme.green
import com.example.newsapp.ui.theme.gray1
import com.example.newsapp.ui.theme.gray2
import com.example.newsapp.ui.theme.gray3
import com.example.newsapp.utils.NavigationDrawerSheet
import com.example.newsapp.utils.NewsTopAppBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NewsScreenContent(

                )
            }

        }
    }

    fun openWebsitesForNews(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}


@Composable
fun NewsScreenContent() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val toolbarTitle = remember {
        mutableIntStateOf(R.string.news_app)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Drawer Sheet  // Side Menu
            NavigationDrawerSheet(onSettingsClick = {
                scope.launch {
                    drawerState.close()
                }
            }, onCategoriesClick = {
                navController.popBackStack()
                if (navController.currentDestination?.route != CategoriesScreen().route) {
                    navController.navigate(CategoriesScreen().route)
                }
                scope.launch {
                    drawerState.close()
                }
            })
        }) {
        Scaffold(
            topBar = {
                // Top App Bar
                NewsTopAppBar(titleResId = toolbarTitle.intValue) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }, modifier = Modifier
                .fillMaxSize()
        )
        { paddingValues ->

            // FrameLayout -> Categories Fragment -> News Fragment
            // enum classes & sealed class
            NavHost(
                navController = navController,
                startDestination = CategoriesScreen().route,
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                //              "categories"
                composable(CategoriesScreen().route) {
                    toolbarTitle.intValue = R.string.news_app
                    CategoriesFragment(navController)
                }
                composable(
                    "${NewsScreen().route}/{category_id}",
                    arguments = listOf(navArgument("category_id") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val categoryId = navBackStackEntry.arguments?.getString("category_id")

                    NewsFragment(
                        category = categoryId ?: "",
                        navHostController = navController,
                        onNewsClick = { title ->
                            navController.navigate("newsDetails/$title")
                        })
                }
                composable("newsDetails/{title}", arguments = listOf(navArgument("title") {
                    type = NavType.StringType
                })) { NavBackStackEntry ->
                    val title = NavBackStackEntry.arguments?.getString("title") ?: ""
                    NewsDetailsScreen(title = title, titleResId = R.string.news_app)
                }

            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsScreenPreview() {
    NewsScreenContent()
}
