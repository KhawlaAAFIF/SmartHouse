package io.github.domi04151309.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class WaterLineChartView : View {
    private val paint = Paint()
    private var xData: FloatArray = floatArrayOf()
    private var yData: FloatArray = floatArrayOf()
    private var minYValue: Float = 0f
    private var maxYValue: Float = 0f
    private var yAxisStart: Float = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.isAntiAlias = true

        val chartWidth = 900f
        val chartHeight = 400f
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()


        val marginLeft = (viewWidth - chartWidth) / 2
        val marginRight = marginLeft

        val marginTop = (viewHeight - chartHeight) / 2
        val marginBottom = marginTop

        canvas?.apply {
            drawColor(Color.WHITE)


            paint.color = Color.BLACK
            drawLine(marginLeft, viewHeight - marginBottom, viewWidth - marginRight, viewHeight - marginBottom, paint)


            minYValue = yData.minOrNull() ?: 0f
            maxYValue = yData.maxOrNull() ?: 0f


            yAxisStart = if (minYValue >= 12f) minYValue - 2f else 0f


            drawLine(marginLeft, viewHeight - marginBottom, marginLeft, marginTop, paint)

            paint.color = Color.BLACK
            paint.textSize = 20f
            val scaleCount = 8
            val scaleStep = (maxYValue - yAxisStart) / scaleCount
            var prevValue = -1f
            for (i in 0 until scaleCount + 1) {
                val scaledValue = (i * scaleStep + yAxisStart).toInt()
                if (scaledValue.toFloat() != prevValue) {
                    val yPos = viewHeight - marginBottom - ((i * scaleStep + yAxisStart) - yAxisStart) * (chartHeight / (maxYValue - yAxisStart))
                    drawLine(marginLeft - 3f, yPos, marginLeft + 5f, yPos, paint)
                    drawText("$scaledValue ", marginLeft - 42f, yPos + 8f, paint)
                    prevValue = scaledValue.toFloat()
                }
            }

            paint.color = Color.LTGRAY
            paint.strokeWidth = 1f
            for (i in 0 until scaleCount + 1) {
                val yPos = viewHeight - marginBottom - ((i * scaleStep + yAxisStart) - yAxisStart) * (chartHeight / (maxYValue - yAxisStart))
                drawLine(marginLeft, yPos, viewWidth - marginRight, yPos, paint)
            }

            paint.color = Color.RED
            paint.strokeWidth = 2f
            val path = Path()
            for (i in xData.indices) {
                val scaledY = viewHeight - marginBottom - (yData[i] - yAxisStart) * (chartHeight / (maxYValue - yAxisStart))
                val xPos = marginLeft + (i * (chartWidth / (xData.size - 1)))
                if (i == 0) {
                    path.moveTo(xPos, scaledY)
                } else {
                    path.lineTo(xPos, scaledY)
                }
                drawCircle(xPos, scaledY, 5f, paint)

                drawText("${xData[i].toInt()}", xPos - 10f, viewHeight - marginBottom + 30f, paint)
            }
            paint.color = Color.BLUE
            drawPath(path, paint)

            paint.color = Color.BLACK
            paint.textSize = 30f
            drawText("Day", viewWidth - marginRight + 15f, viewHeight - marginBottom, paint)
            drawText("Water Consumption (Liters) in May", marginLeft - 30f, marginTop - 40f, paint)
        }
    }

    fun setData(xData: FloatArray, yData: FloatArray, dates: Array<String>) {
        this.xData = xData
        this.yData = yData
        invalidate()
    }
}
