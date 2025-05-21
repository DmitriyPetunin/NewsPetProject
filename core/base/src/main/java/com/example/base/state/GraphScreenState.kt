package com.example.base.state

import androidx.compose.runtime.Immutable

@Immutable
data class GraphScreenState(
    val count: String = "",
    val points: String = "",
    val isGraphVisible: Boolean = false,
)