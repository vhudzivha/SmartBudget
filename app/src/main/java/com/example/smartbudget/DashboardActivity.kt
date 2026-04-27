package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var totalText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val addBtn = findViewById<Button>(R.id.addBtn)
        recycler = findViewById(R.id.expenseList)
        totalText = findViewById(R.id.totalText)

        addBtn.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
    }

    private fun loadExpenses() {

        val sharedPref = getSharedPreferences("SmartBudget", MODE_PRIVATE)
        val data = sharedPref.getString("expenses", "") ?: ""

        val expenseList = mutableListOf<Expense>()
        var total = 0.0

        if (data.isNotEmpty()) {
            val records = data.split(";")

            for (record in records) {
                if (record.isNotEmpty()) {
                    val parts = record.split(",")

                    if (parts.size >= 3) {
                        val amount = parts[0]
                        val category = parts[1]
                        val description = parts[2]

                        expenseList.add(Expense(amount, category, description))
                        total += amount.toDoubleOrNull() ?: 0.0
                    }
                }
            }
        }

        if (expenseList.isEmpty()) {
            totalText.text = "No expenses yet"
        } else {
            totalText.text = "Total: R$total"
        }

        recycler.adapter = ExpenseAdapter(this, expenseList)
    }
}