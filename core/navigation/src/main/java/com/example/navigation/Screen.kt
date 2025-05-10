package com.example.navigation

sealed class Screen(val route:String) {

    object ListNewsScreen: Screen(route = "listNewsScreen")
    object DetailNewsScreen: Screen(route = "detailNewsScreen")
    object SearchNewsScreen: Screen(route = "searchNewsScreen")
}