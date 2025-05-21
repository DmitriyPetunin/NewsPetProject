package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.GraphScreenAction
import com.example.base.event.GraphScreenEvent
import com.example.base.state.GraphScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GraphViewModel @Inject constructor(

) : ViewModel() {

    private val _graphScreenState: MutableStateFlow<GraphScreenState> =
        MutableStateFlow(GraphScreenState())
    val graphScreenState = _graphScreenState.asStateFlow()

    private val _event = MutableSharedFlow<GraphScreenEvent>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val event: SharedFlow<GraphScreenEvent> = _event


    private fun drawGraph() {

        val currentState = graphScreenState.value

        val count = currentState.count
        val points = currentState.points

        if (count.isEmpty()){
            _event.tryEmit(GraphScreenEvent.ShowErrorMessage("пустое значение"))
            return
        }

        val counter = count.trim().toIntOrNull() ?: run {
            _event.tryEmit(GraphScreenEvent.ShowErrorMessage("не валидное значение ${count}"))
            return
        }

        if (points.isEmpty()) {
            _event.tryEmit(GraphScreenEvent.ShowErrorMessage("пустое значение для списка"))
            return
        }

        val pointsList = points.split(",").mapNotNull { it.trim().toIntOrNull() }
        if (pointsList.size != points.split(",").size) {
            _event.tryEmit(GraphScreenEvent.ShowErrorMessage("некорректные значения в списке"))
            return
        }

        if (!check(counter,pointsList)){
            _event.tryEmit(GraphScreenEvent.ShowErrorMessage("несовпадают кол-ва значений"))
        } else {
            _graphScreenState.update {
                it.copy(isGraphVisible = true)
            }
            _event.tryEmit(GraphScreenEvent.DrawGraph)
        }

    }


    fun onAction(action: GraphScreenAction) {
        Log.d("TEST-TAG", "зашли в onAction")
        when (action) {
            is GraphScreenAction.ClickButton -> {
                drawGraph()
            }

            is GraphScreenAction.UpdateCountField -> {
                updateCounter(action.input)
            }

            is GraphScreenAction.UpdatePointField -> {
                updatePoints(action.input)
            }
        }
    }

    private fun updateCounter(input: String) {
        _graphScreenState.update {
            it.copy(count = input, isGraphVisible = false)
        }
    }

    private fun updatePoints(input: String) {
        _graphScreenState.update {
            it.copy(points = input, isGraphVisible = false)
        }
    }


    private fun check(count: Int, points: List<Int>): Boolean {
        return count == points.size
    }
}