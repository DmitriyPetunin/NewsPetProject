package com.example.newspetproject.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.detailpage.DetailNewsScreen
import com.example.graph.GraphScreen
import com.example.mainpage.ListNewsScreen
import com.example.navigation.AppNavGraph
import com.example.navigation.Screen
import com.example.presentation.viewmodel.GraphViewModel
import com.example.presentation.viewmodel.NewsViewModel
import com.example.searchpage.SearchPageScreen


@Composable
fun MainScreen(
    modifier: Modifier
) {

    val navController = rememberNavController()

    val newsViewModel: NewsViewModel = hiltViewModel()

    val graphViewModel: GraphViewModel = hiltViewModel()
    AppNavGraph(
        startDestination = Screen.GraphScreen.route,

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
        },
        graphScreen = {
            GraphScreen(graphViewModel = graphViewModel)
        }
    )
}