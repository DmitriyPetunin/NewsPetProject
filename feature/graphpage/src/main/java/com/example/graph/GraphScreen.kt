package com.example.graph

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.base.action.GraphScreenAction
import com.example.base.event.GraphScreenEvent
import com.example.presentation.viewmodel.GraphViewModel


@Composable
fun GraphScreen(
    graphViewModel: GraphViewModel
){

    val context = LocalContext.current

    val state by graphViewModel.graphScreenState.collectAsState()

    LaunchedEffect(Unit) {
        graphViewModel.event.collect{ event ->
            when(event){
                is GraphScreenEvent.ShowErrorMessage -> {
                    Toast.makeText(context,event.message,Toast.LENGTH_LONG).show()
                }
                is GraphScreenEvent.DrawGraph -> {
                    Toast.makeText(context,"ура рисуем граф",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
        ,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            text = "Экран графика",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displaySmall
        )

        OutlinedTextField(
            value = state.count,
            onValueChange = { graphViewModel.onAction(GraphScreenAction.UpdateCountField(it)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )


        OutlinedTextField(
            value = state.points,
            onValueChange = { graphViewModel.onAction(GraphScreenAction.UpdatePointField(it))},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )


        Button(
            onClick = {
                graphViewModel.onAction(GraphScreenAction.ClickButton)
        }) {
            Text(
                text = "покажи график",
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.isGraphVisible) {
                GraphItem(
                    listOfY = state.points.split(",").map { it.toInt() },
                    paddingSpace = 16.dp,
                    graphAppearance = GraphAppearance(
                        graphColor = Color.White,
                        graphAxisColor = MaterialTheme.colorScheme.primary,
                        graphThickness = 1f,
                        iscolorAreaUnderChart = true,
                        colorAreaUnderChart = MaterialTheme.colorScheme.primaryContainer,
                        isCircleVisible = true,
                        circleColor = MaterialTheme.colorScheme.tertiary,
                        backgroundColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}