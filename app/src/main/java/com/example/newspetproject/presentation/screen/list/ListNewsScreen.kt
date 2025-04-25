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
import com.example.newspetproject.presentation.viewmodel.NewsViewModel


@Composable
fun ListNewsScreen(
    navController: NavController,
    newsViewModel:NewsViewModel
) {

    val news = newsViewModel.news.observeAsState(emptyList())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(news.value){ article ->
                ArticleListItem(article, onItemClick = {navController.navigate(Screen.DetailNewsScreen.route)})
            }
        }
    }


}