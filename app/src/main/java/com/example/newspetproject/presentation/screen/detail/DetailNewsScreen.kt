package com.example.newspetproject.presentation.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.newspetproject.presentation.viewmodel.NewsViewModel


@Composable
fun DetailNewsScreen(
    modifier: Modifier,
    id:String,
    newsViewModel: NewsViewModel,
) {

    val detailNews by newsViewModel.detailNews.observeAsState(null)

    LaunchedEffect(Unit) {
        newsViewModel.fetchDetailNews(id)
    }


    if (detailNews != null){
        Column(
            modifier = modifier
                .fillMaxSize(),
        ) {
            Text(text = detailNews!!.title)
        }
    }

}