package com.example.base.event

sealed class GraphScreenEvent {
    data class ShowErrorMessage(val message: String):GraphScreenEvent()

    data object DrawGraph:GraphScreenEvent()
}