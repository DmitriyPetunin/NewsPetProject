package com.example.searchpage

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.base_feature.ui.ArticleListItem
import com.example.base_feature.ui.ErrorScreen
import com.example.base_feature.ui.ShimmerListItem
import com.example.domain.model.list.Article
import com.example.presentation.ui.NewsState
import com.example.presentation.ui.SourceStatus
import com.example.base_feature.viewmodel.NewsViewModel

@Composable
fun SearchPageScreen(
    modifier: Modifier,
    newsViewModel: NewsViewModel
) {

    var input by remember {  mutableStateOf("") }

    val news by newsViewModel.searchNewsState.collectAsState()


    val context = LocalContext.current
    LaunchedEffect(news) {
        val sourceMessage = when (news.source) {
            SourceStatus.API -> "Данные получены из API"
            SourceStatus.DB -> "Данные получены из базы данных"
            SourceStatus.LOADING -> "Загрузка данных..."
            SourceStatus.ERROR -> "Произошла ошибка при загрузке данных"
        }
        Toast.makeText(context, sourceMessage, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Search Page",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            TextField(
                value = input,
                onValueChange = {it -> input = it },
                modifier = Modifier
                    .weight(0.55F)
            )

            IconButton(
                modifier = Modifier
                    .weight(0.2F),
                onClick = { newsViewModel.search(input) }
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }

        Text(text = "новости по запросу $input")

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(news.news){ article ->
                when(article){
                    is NewsState.NewsContent -> {
                        ArticleListItem(
                            article = article.item as Article,
                            onItemClick = {}
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