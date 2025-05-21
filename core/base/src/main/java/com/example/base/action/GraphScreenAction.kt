package com.example.base.action

sealed interface GraphScreenAction {
    data object ClickButton:GraphScreenAction

    data class UpdateCountField(val input: String):GraphScreenAction
    data class UpdatePointField(val input: String):GraphScreenAction
}