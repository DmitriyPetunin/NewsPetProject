package com.example.newspetproject.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.domain.model.detail.NewsDetail


@Composable
fun AppNavGraph(
    navController: NavHostController,
    listNewsScreen: @Composable ()-> Unit,
    detailNewsScreen: @Composable (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ListNewsScreen.route
    ){

        composable(route = Screen.ListNewsScreen.route){
            listNewsScreen()
        }

        composable(
            route = "${Screen.DetailNewsScreen.route}/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.StringType })
        ){ navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString("newsId")
            if (productId != null) {
                Log.d("TEST-TAG", "ошибки нет")
                detailNewsScreen(productId)
            } else {
                Text(text = "Ошибка: newsId не найден")
            }
        }
    }
}