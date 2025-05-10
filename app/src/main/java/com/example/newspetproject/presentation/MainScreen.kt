package com.example.newspetproject.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.navigation.AppNavGraph
import com.example.detailpage.DetailNewsScreen
import com.example.mainpage.ListNewsScreen
import com.example.base_feature.viewmodel.NewsViewModel
import com.example.searchpage.SearchPageScreen


@Composable
fun MainScreen(
    modifier: Modifier
) {

    val navController = rememberNavController()

    val newsViewModel: NewsViewModel = hiltViewModel()

    AppNavGraph(
        navController = navController,
        listNewsScreen = {
            ListNewsScreen(
                modifier = modifier,
                navController = navController,
                newsViewModel = newsViewModel
            )
        },
        detailNewsScreen = { id ->
            DetailNewsScreen(
                modifier = modifier,
                id = id,
                newsViewModel = newsViewModel
            )
        },
        searchNewsScreen = {
            SearchPageScreen(
                modifier = modifier,
                newsViewModel = newsViewModel
            )
        }
    )
}