package com.example.newsapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import com.example.newsapp.fragments.categories.CategoriesScreen
import com.example.newsapp.fragments.newsDetails.NewsDetailsScreen
import com.example.newsapp.fragments.news.NewsScreen
import com.example.newsapp.fragments.categories.CategoriesViewModel
import com.example.newsapp.model.CategoriesScreen
import com.example.newsapp.model.NewsScreen
import com.example.newsapp.utils.NavigationDrawerSheet
import com.example.newsapp.utils.NewsTopAppBar


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
                    CategoriesScreen(vm = CategoriesViewModel(),navController)
                }
                composable(
                    "${NewsScreen().route}/{category_id}",
                    arguments = listOf(navArgument("category_id") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val categoryId = navBackStackEntry.arguments?.getString("category_id")

                    NewsScreen(
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
