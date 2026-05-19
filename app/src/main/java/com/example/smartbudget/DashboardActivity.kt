package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView

    private lateinit var totalText: TextView

    private lateinit var budgetText: TextView

    private lateinit var remainingText: TextView

    private lateinit var warningText: TextView

    private lateinit var badgeText: TextView

    private lateinit var rewardText: TextView

    private lateinit var budgetProgress: ProgressBar

    private lateinit var filterSpinner: Spinner

    private lateinit var expenseList: MutableList<Expense>

    private val budgetGoal = 5000

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        val addBtn =
            findViewById<Button>(R.id.addBtn)

        val graphBtn =
            findViewById<Button>(R.id.graphBtn)

        recycler =
            findViewById(R.id.expenseList)

        totalText =
            findViewById(R.id.totalText)

        budgetText =
            findViewById(R.id.budgetText)

        remainingText =
            findViewById(R.id.remainingText)

        warningText =
            findViewById(R.id.warningText)

        badgeText =
            findViewById(R.id.badgeText)

        rewardText =
            findViewById(R.id.rewardText)

        budgetProgress =
            findViewById(R.id.budgetProgress)

        filterSpinner =
            findViewById(R.id.filterSpinner)

        expenseList = mutableListOf()

        recycler.layoutManager =
            LinearLayoutManager(this)

        budgetText.text =
            "Budget Goal: R$budgetGoal"

        val filters = arrayOf(
            "All Expenses",
            "Today",
            "This Month"
        )

        val filterAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                filters
            )

        filterSpinner.adapter =
            filterAdapter

        filterSpinner.setOnItemSelectedListener(
            object :
                android.widget.AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {

                    loadExpenses()
                }

                override fun onNothingSelected(
                    parent: android.widget.AdapterView<*>?
                ) {
                }
            }
        )

        loadExpenses()

        addBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddExpenseActivity::class.java
                )
            )
        }

        graphBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    GraphActivity::class.java
                )
            )
        }
    }

    override fun onResume() {

        super.onResume()

        loadExpenses()
    }

    private fun loadExpenses() {

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

        expenseList.clear()

        var total = 0.0

        val selectedFilter =
            filterSpinner.selectedItem.toString()

        val todayDate =
            SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            ).format(Date())

        val currentMonth =
            SimpleDateFormat(
                "MMM yyyy",
                Locale.getDefault()
            ).format(Date())

        if (data.isNotEmpty()) {

            val records = data.split(";")

            for (record in records) {

                if (record.isNotEmpty()) {

                    val parts = record.split(",")

                    if (parts.size >= 4) {

                        val amount = parts[0]

                        val category = parts[1]

                        val description = parts[2]

                        val date = parts[3]

                        var shouldAdd = false

                        when (selectedFilter) {

                            "All Expenses" -> {
                                shouldAdd = true
                            }

                            "Today" -> {
                                shouldAdd =
                                    date == todayDate
                            }

                            "This Month" -> {
                                shouldAdd =
                                    date.contains(currentMonth)
                            }
                        }

                        if (shouldAdd) {

                            expenseList.add(
                                Expense(
                                    amount,
                                    category,
                                    description,
                                    date
                                )
                            )

                            total +=
                                amount.toDoubleOrNull() ?: 0.0
                        }
                    }
                }
            }
        }

        totalText.text = "R$total"

        val remaining =
            budgetGoal - total

        remainingText.text =
            "Remaining: R$remaining"

        budgetProgress.progress =
            total.toInt()

        // Budget Warning Logic

        if (total >= budgetGoal) {

            warningText.text =
                "⚠ Budget exceeded!"

        } else if (total >= budgetGoal * 0.8) {

            warningText.text =
                "⚠ Warning: Near budget limit!"

        } else {

            warningText.text = ""
        }

        // Achievement Badge Logic

        if (total <= 1000) {

            badgeText.text =
                "🥉 Budget Beginner"

            rewardText.text =
                "Great start! Keep tracking expenses."

        } else if (total <= 3000) {

            badgeText.text =
                "🥈 Smart Saver"

            rewardText.text =
                "Nice work staying within budget!"

        } else if (total <= 5000) {

            badgeText.text =
                "🥇 Budget Master"

            rewardText.text =
                "Excellent budgeting skills!"

        } else {

            badgeText.text =
                "🔥 Overspending Alert"

            rewardText.text =
                "Try reducing unnecessary expenses."
        }

        recycler.adapter =
            ExpenseAdapter(
                this,
                expenseList
            )
    }
}