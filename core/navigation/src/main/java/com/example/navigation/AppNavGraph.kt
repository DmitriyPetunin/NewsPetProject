package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
fun AppNavGraph(
    navController: NavHostController,
    listNewsScreen: @Composable () -> Unit,
    detailNewsScreen: @Composable (String?) -> Unit,
    searchNewsScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SearchNewsScreen.route
    ){

        composable(route = Screen.ListNewsScreen.route){
            listNewsScreen()
        }

        composable(route = Screen.SearchNewsScreen.route){
            searchNewsScreen()
        }

        composable(
            route = "${Screen.DetailNewsScreen.route}/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.StringType })
        ){ navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString("newsId")
                detailNewsScreen(productId)
        }
    }
}