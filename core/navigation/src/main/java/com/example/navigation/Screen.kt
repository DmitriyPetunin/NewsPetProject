package com.example.navigation

sealed class Screen(val route:String) {

    data object ListNewsScreen: Screen(route = "listNewsScreen")
    data object DetailNewsScreen: Screen(route = "detailNewsScreen")
    data object SearchNewsScreen: Screen(route = "searchNewsScreen")

    data object GraphScreen: Screen(route = "graphScreen")
}