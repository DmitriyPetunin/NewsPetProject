package com.example.newspetproject.navigation

sealed class Screen(val route:String) {

    object ListNewsScreen:Screen(route = "listNewsScreen")
    object DetailNewsScreen:Screen(route = "detailNewsScreen")
}