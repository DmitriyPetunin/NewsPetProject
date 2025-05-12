package com.example.mainpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.base.model.list.Article
import com.example.base.state.NewsState
import com.example.base_feature.ui.ArticleListItem
import com.example.base_feature.ui.ErrorScreen
import com.example.base_feature.ui.ShimmerListItem
import com.example.navigation.Screen
import com.example.presentation.viewmodel.NewsViewModel


@Composable
fun ListNewsScreen(
    modifier: Modifier,
    navController: NavController,
    newsViewModel: NewsViewModel
) {

    val news by newsViewModel.state.collectAsState()

    //val listOfNews = news!!.news

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
            items(news.news) { article ->
                when (article) {
                    is NewsState.NewsContent -> {
                        ArticleListItem(
                            article = article.item as Article,
                            onItemClick = {
                                navigateToDetailScreen(
                                    navController = navController,
                                    articleId = (article.item as com.example.base.model.list.Article).uuid
                                )
                            }
                        )
                    }
                    is NewsState.NewsLoading -> {
                        ShimmerListItem(
                            modifier = modifier
                        )
                    }
                    is NewsState.NewsError -> {
                        ErrorScreen(
                            modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(16.dp),
                            messageText = article.message
                        )
                    }
                }
            }
        }
    }
}

fun navigateToDetailScreen(navController: NavController, articleId: String) =
    navController.navigate("${Screen.DetailNewsScreen.route}/${articleId}")