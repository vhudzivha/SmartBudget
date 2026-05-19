package com.example.smartbudget

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_graph)

        val pieChart =
            findViewById<PieChart>(R.id.pieChart)

        val minGoalText =
            findViewById<TextView>(R.id.minGoalText)

        val maxGoalText =
            findViewById<TextView>(R.id.maxGoalText)

        val statusText =
            findViewById<TextView>(R.id.statusText)

        val minimumGoal = 1000

        val maximumGoal = 5000

        val sharedPref =
            getSharedPreferences(
                "SmartBudget",
                MODE_PRIVATE
            )

        val data =
            sharedPref.getString(
                "expenses",
                ""
            ) ?: ""

        val categoryTotals =
            mutableMapOf<String, Float>()

        var totalSpent = 0f

        if (data.isNotEmpty()) {

            val records = data.split(";")

            for (record in records) {

                if (record.isNotEmpty()) {

                    val parts = record.split(",")

                    if (parts.size >= 4) {

                        val amount =
                            parts[0].toFloatOrNull() ?: 0f

                        val category =
                            parts[1]

                        totalSpent += amount

                        categoryTotals[category] =
                            categoryTotals.getOrDefault(
                                category,
                                0f
                            ) + amount
                    }
                }
            }
        }

        val entries = ArrayList<PieEntry>()

        for ((category, total) in categoryTotals) {

            entries.add(
                PieEntry(
                    total,
                    category
                )
            )
        }

        val dataSet =
            PieDataSet(
                entries,
                "Expenses"
            )

        dataSet.colors = listOf(
            Color.parseColor("#6C63FF"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#F44336"),
            Color.parseColor("#03A9F4")
        )

        val pieData =
            PieData(dataSet)

        pieChart.data =
            pieData

        pieChart.centerText =
            "SmartBudget"

        pieChart.animateY(1500)

        pieChart.invalidate()

        // Goal Analytics

        minGoalText.text =
            "Minimum Goal: R$minimumGoal"


        maxGoalText.text =
            "Maximum Goal: R$maximumGoal"

        if (totalSpent < minimumGoal) {

            statusText.text =
                "Status: Excellent Saving"

        } else if (totalSpent <= maximumGoal) {

            statusText.text =
                "Status: Good Budget Control"

        } else {

            statusText.text =
                "Status: Overspending"
        }
    }
}