package com.example.newspetproject.presentation.screen.list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newspetproject.navigation.Screen
import com.example.newspetproject.presentation.screen.ShimmerListItem
import com.example.newspetproject.presentation.viewmodel.NewsViewModel


@Composable
fun ListNewsScreen(
    modifier: Modifier,
    navController: NavController,
    newsViewModel: NewsViewModel
) {

    val news by newsViewModel.news.observeAsState(emptyList())

    val isLoading by newsViewModel.isLoading.observeAsState(true)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            if (isLoading) {
                items(10) {
                    ShimmerListItem(
                        isLoading = true,
                        contentAfterLoading = {},
                        modifier = modifier
                    )
                }
            } else {
                Log.d("TEST-TAG", "LazyColumn")
                items(news) { article ->
                    Log.d("TEST-TAG", "isLoading = $isLoading")
                    ShimmerListItem(
                        isLoading = isLoading,
                        contentAfterLoading = {
                            ArticleListItem(
                                article = article,
                                onItemClick = {
                                    navigateToDetailScreen(
                                        navController = navController,
                                        articleId = article.uuid
                                    )
                                }
                            )
                        },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

fun navigateToDetailScreen(navController: NavController, articleId: String) =
    navController.navigate("${Screen.DetailNewsScreen.route}/${articleId}")