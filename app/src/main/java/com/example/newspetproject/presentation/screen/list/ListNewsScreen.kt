package com.example.newspetproject.presentation.screen.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.LocaleList
import com.example.domain.model.list.Article
import com.example.newspetproject.navigation.Screen
import com.example.newspetproject.presentation.NewsScreenState
import com.example.newspetproject.presentation.NewsState
import com.example.newspetproject.presentation.screen.ShimmerListItem
import com.example.newspetproject.presentation.viewmodel.NewsViewModel


@Composable
fun ListNewsScreen(
    modifier: Modifier,
    navController: NavController,
    newsViewModel: NewsViewModel
) {

    val news by newsViewModel.state.observeAsState(NewsScreenState(emptyList()))

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
                                    articleId = article.item.uuid
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
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = article.message,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

fun navigateToDetailScreen(navController: NavController, articleId: String) =
    navController.navigate("${Screen.DetailNewsScreen.route}/${articleId}")