package io.github.domi04151309.home.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.domi04151309.home.R
import io.github.domi04151309.home.WaterLineChartView

class WaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)

        val waterLineChartView = findViewById<WaterLineChartView>(R.id.waterLineChartView)
        val textViewYesterday = findViewById<TextView>(R.id.textViewYesterday)
        val textViewToday = findViewById<TextView>(R.id.textViewToday)

        val csvFile = assets.open("water.csv")
        val reader = csvFile.bufferedReader()
        val data = reader.readLines()

        val xData = mutableListOf<Float>()
        val yData = mutableListOf<Float>()
        val dates = mutableListOf<String>()

        for (i in 1 until data.size) {
            val row = data[i].split(",")
            dates.add(row[0])
            xData.add(i.toFloat())
            yData.add(row[1].toFloat())
        }

        waterLineChartView.setData(xData.toFloatArray(), yData.toFloatArray(), dates.toTypedArray())

        val yesterdayIndex = yData.size - 2
        val todayIndex = yData.size - 1
        val yesterdayConsumption = yData[yesterdayIndex]
        val todayConsumption = yData[todayIndex]

        textViewYesterday.text = "Yesterday: ${yesterdayConsumption.toInt()} L"
        textViewToday.text = "Today: ${todayConsumption.toInt()} L"
    }
}
