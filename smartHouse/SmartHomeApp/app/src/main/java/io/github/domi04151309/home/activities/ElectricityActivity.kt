package io.github.domi04151309.home.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.domi04151309.home.R
import io.github.domi04151309.home.LineChartView


class ElectricityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electrecity)


        val lineChartView = findViewById<LineChartView>(R.id.lineChartView)
        val textViewYesterday = findViewById<TextView>(R.id.textViewYesterday)
        val textViewToday = findViewById<TextView>(R.id.textViewToday)

        val csvFile = assets.open("electricity.csv")
        val reader = csvFile.bufferedReader()
        val data = reader.readLines()

        val xData = mutableListOf<Float>()
        val yData = mutableListOf<Float>()

        for (i in 1 until data.size) {
            val row = data[i].split(",")
            xData.add(i.toFloat())
            yData.add(row[1].toFloat())
        }

        lineChartView.setData(xData.toFloatArray(), yData.toFloatArray())


        val yesterdayIndex = yData.size - 2
        val todayIndex = yData.size - 1
        val yesterdayConsumption = yData[yesterdayIndex]
        val todayConsumption = yData[todayIndex]

        textViewYesterday.text = "Yesterday: $yesterdayConsumption kWh"
        textViewToday.text = "Today: $todayConsumption kWh"
    }
}
