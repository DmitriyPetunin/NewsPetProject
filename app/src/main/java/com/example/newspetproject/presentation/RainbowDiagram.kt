package com.example.newspetproject.presentation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.newspetproject.R
import kotlin.math.*

class RainbowDiagram @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet,
    defStyleAttr: Int = 0
):View(context,attrs,defStyleAttr) {


    private var sectorsCount: Int = 1
        set(value) {
            field = value
            invalidate()
        }


    private var colors: List<Int> = listOf(Color.RED, Color.GREEN, Color.BLUE)
        set(value) {
            field = value
            invalidate()
        }


    private var activeSector: Int = -1
    private var innerRadiusRatio = 0.6f

    private val centerTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val path = Path()

    private val outerRectF = RectF()
    private val innerRectF = RectF()

    private var centerX = 0f
    private var centerY = 0f

    private var outerRadius = 0f
    private var innerRadius = 0f

    init {
        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.RainbowDiagram).use { ta ->
            this.sectorsCount = ta.getInt(R.styleable.RainbowDiagram_sectors_count, 3)
            val colorsId = ta.getResourceId(R.styleable.RainbowDiagram_colors_array,0)
            if(colorsId != 0){
                this.colors = context.resources.getIntArray(colorsId).toList()
            } else {
                this.colors = listOf(Color.RED, Color.GREEN, Color.BLUE)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val minSize = min(width, height).toFloat()
        outerRadius = minSize * 0.4f
        innerRadius = outerRadius * innerRadiusRatio
        centerX = width / 2f
        centerY = height / 2f

        outerRectF.set(
            centerX - outerRadius,
            centerY - outerRadius,
            centerX + outerRadius,
            centerY + outerRadius
        )

        innerRectF.set(
            centerX - innerRadius,
            centerY - innerRadius,
            centerX + innerRadius,
            centerY + innerRadius
        )

        centerTextPaint.textSize = outerRadius * 0.5f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (sectorsCount <= 0) return

        val sweepAngle = 360f / sectorsCount
        var startAngle = -90f // Start at 9 o'clock

        for (i in 0 until sectorsCount) {
            val isActive = i == activeSector
            val color = if (isActive) lightenColor(colors[i]) else colors[i]

            drawRainbowSector(canvas, startAngle, sweepAngle, color)
            startAngle += sweepAngle
        }

        startAngle = -90f
        for (i in 0 until sectorsCount) {
            val isActive = i == activeSector
            val color = if (isActive) lightenColor(colors[i]) else colors[i]
            drawRainbowSectorWithoutCircle(canvas, startAngle, sweepAngle, color)
            startAngle += sweepAngle

        }

        canvas.drawText(sectorsCount.toString(), centerX, centerY - (centerTextPaint.ascent() + centerTextPaint.descent()) / 2, centerTextPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                checkSectorTouched(x, y)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun drawRainbowSector(canvas: Canvas, startAngle: Float, sweepAngle: Float, color: Int) {

        path.reset()
        path.arcTo(outerRectF, startAngle, sweepAngle, false)

        val endAngle = startAngle + sweepAngle
        val innerEndX = centerX + innerRadius * cos(Math.toRadians(endAngle.toDouble())).toFloat()
        val innerEndY = centerY + innerRadius * sin(Math.toRadians(endAngle.toDouble())).toFloat()

        path.lineTo(innerEndX, innerEndY)
        path.arcTo(innerRectF, endAngle, -sweepAngle, false)
        path.close()

        paint.color = color
        canvas.drawPath(path, paint)
    }

    private fun drawRainbowSectorWithoutCircle(canvas: Canvas, startAngle: Float, sweepAngle: Float, color: Int) {

        val endAngle = startAngle + sweepAngle

        val circleRadius = outerRadius * 0.2f
        val circleDistance = outerRadius - (outerRadius - innerRadius) * 0.5f
        val circleX = centerX + circleDistance * cos(Math.toRadians(endAngle.toDouble())).toFloat()
        val circleY = centerY + circleDistance * sin(Math.toRadians(endAngle.toDouble())).toFloat()

        circlePaint.color = color
        canvas.drawCircle(circleX, circleY, circleRadius, circlePaint)
    }

    private fun lightenColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[1] *= 0.7f
        hsv[2] = min(1f, hsv[2] * 1.3f)
        return Color.HSVToColor(hsv)
    }

    private fun checkSectorTouched(x: Float, y: Float) {
        val distance = sqrt((x - centerX).pow(2) + (y - centerY).pow(2))

        val sweepAngle = 360f / sectorsCount
        var startAngle = -90f

        for (i in 0 until sectorsCount) {
            if (isTouchOnCircle(x, y, startAngle, sweepAngle)) {
                activeSector = if (i == activeSector) -1 else i
                invalidate()
                return
            }
            startAngle += sweepAngle
        }

        if (distance > outerRadius || distance < innerRadius) {
            resetFocus()
            return
        }

        val angle = Math.toDegrees(atan2(y - centerY, x - centerX).toDouble())
        val normalizedAngle = (angle + 90 + 360) % 360
        val sectorAngle = 360f / sectorsCount

        val sector = (normalizedAngle / sectorAngle).toInt()
        activeSector = if (sector == activeSector) -1 else sector
        invalidate()
    }

    private fun isTouchOnCircle(x: Float, y: Float, startAngle: Float, sweepAngle: Float): Boolean {
        val endAngle = startAngle + sweepAngle
        val circleDistance = outerRadius - (outerRadius - innerRadius) * 0.5f
        val circleX = centerX + circleDistance * cos(Math.toRadians(endAngle.toDouble())).toFloat()
        val circleY = centerY + circleDistance * sin(Math.toRadians(endAngle.toDouble())).toFloat()
        val circleRadius = outerRadius * 0.2f
//        val touchRadius = circleRadius * 1.2f

        val dx = x - circleX
        val dy = y - circleY
        return sqrt((dx * dx + dy * dy).toDouble()) <= circleRadius
    }

    private fun resetFocus() {
        activeSector = -1
        invalidate()
    }
}

