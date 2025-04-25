package com.example.newspetproject.presentation.screen.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.newspetproject.navigation.AppNavGraph
import com.example.newspetproject.presentation.screen.detail.DetailNewsScreen
import com.example.newspetproject.presentation.screen.list.ListNewsScreen
import com.example.newspetproject.presentation.viewmodel.NewsViewModel


@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val newsViewModel:NewsViewModel = hiltViewModel()


    AppNavGraph(
        navController = navController,
        listNewsScreen = {
            ListNewsScreen(navController = navController, newsViewModel = newsViewModel)
        },
        detailNewsScreen = { id -> DetailNewsScreen(navController = navController,id = id) }
    )
}