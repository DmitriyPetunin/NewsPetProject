package com.example.graph

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GraphItem(
    listOfY: List<Int>,
    paddingSpace: Dp,
    graphAppearance: GraphAppearance
) {
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = graphAppearance.graphAxisColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    val y = listOfY.map { it.toFloat() }
    val x = List(y.size) { it }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(graphAppearance.backgroundColor)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height


            val padding = paddingSpace.toPx()
            val graphWidth = canvasWidth - 2 * padding
            val graphHeight = canvasHeight - 2 * padding

            val maxDataPoint = y.maxOrNull() ?: 0f


            // Шаг по оси X
            val stepX = graphWidth / (x.size - 1)

            // Рисуем сетку
            drawGrid(x.size, graphWidth, graphHeight, padding)

            // Отображаем подписи оси X
            for (i in x.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    i.toString(),
                    padding + stepX * i,
                    canvasHeight - padding + 20,
                    textPaint
                )
            }

            // Отображаем подписи оси Y
            val stepY = graphHeight / 5f
            for (i in 0..5) {
                val value = maxDataPoint * i / 5f
                drawContext.canvas.nativeCanvas.drawText(
                    "%.1f".format(value),
                    padding / 2,
                    canvasHeight - padding - stepY * i,
                    textPaint
                )
            }

            // Рисуем точки и линии
            for (i in y.indices) {
                val x1 = padding + stepX * i
                val y1 = canvasHeight - padding - normalizeY(y[i], maxDataPoint = maxDataPoint, graphHeight = graphHeight)
                coordinates.add(PointF(x1, y1))

                // Рисуем точки
                if (graphAppearance.isCircleVisible) {
                    drawCircle(
                        color = graphAppearance.circleColor,
                        radius = 10f,
                        center = Offset(x1, y1)
                    )
                }

                if (y[i] == maxDataPoint) {
                    drawContext.canvas.nativeCanvas.drawText(
                        y[i].toString(),
                        x1,
                        y1 - 20,
                        Paint().apply {
                            color = Color.Black.toArgb()
                            textSize = 32f
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }

            // Рисуем линию графика
            val path = Path().apply {
                reset()
                moveTo(coordinates.first().x, coordinates.first().y)
                for (i in 1 until coordinates.size) {
                    lineTo(coordinates[i].x, coordinates[i].y)
                }
            }
            drawPath(
                path = path,
                color = graphAppearance.graphColor,
                style = Stroke(width = graphAppearance.graphThickness, cap = StrokeCap.Round)
            )

            // Заполняем область под графиком
            if (graphAppearance.iscolorAreaUnderChart) {
                val fillPath = android.graphics.Path(path.asAndroidPath())
                    .asComposePath()
                    .apply {
                        lineTo(canvasWidth - padding, canvasHeight - padding)
                        lineTo(padding, canvasHeight - padding)
                        close()
                    }
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            graphAppearance.colorAreaUnderChart,
                            Color.Transparent
                        ),
                        startY = padding,
                        endY = canvasHeight - padding
                    )
                )
            }
        }
    }
}
private fun normalizeY(value: Float,graphHeight:Float,maxDataPoint:Float): Float {
    return graphHeight * value / maxDataPoint
}
private fun DrawScope.drawGrid(
    xCount: Int,
    graphWidth: Float,
    graphHeight: Float,
    padding: Float,
) {
    val stepX = graphWidth / xCount // Шаг по оси X
    val stepY = graphHeight / 5f // Разбивка оси Y на 5 частей

    // Рисуем горизонтальные линии
    for (i in 0..5) {
        drawLine(
            start = Offset(padding, graphHeight - i * stepY),
            end = Offset(graphWidth + padding, graphHeight - i * stepY),
            color = Color.Gray.copy(alpha = 0.5f),
            strokeWidth = 1f
        )
    }

    // Рисуем вертикальные линии
    for (i in 0 until xCount) {
        drawLine(
            start = Offset(padding + i * stepX, padding),
            end = Offset(padding + i * stepX, graphHeight + padding),
            color = Color.Gray.copy(alpha = 0.5f),
            strokeWidth = 1f
        )

    }
}
