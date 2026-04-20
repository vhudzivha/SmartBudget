package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val addBtn = findViewById<Button>(R.id.addExpenseBtn)

        // Go to Add Expense screen
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // Load data first time
        loadExpenses()
    }

    // 🔁 Auto refresh when returning
    override fun onResume() {
        super.onResume()
        loadExpenses()
    }

    private fun loadExpenses() {

        val recycler = findViewById<RecyclerView>(R.id.expenseList)
        val totalText = findViewById<TextView>(R.id.totalText)

        val sharedPref = getSharedPreferences("SmartBudget", MODE_PRIVATE)
        val data = sharedPref.getString("expenses", "") ?: ""

        val expenseList = mutableListOf<Expense>()
        var total = 0.0

        if (data.isNotEmpty()) {
            val records = data.split(";")

            for (record in records) {
                if (record.isNotEmpty()) {
                    val parts = record.split(",")

                    val amount = parts[0]
                    val category = parts[1]
                    val description = parts[2]

                    expenseList.add(Expense(amount, category, description))

                    // ✅ Safe conversion
                    total += amount.toDoubleOrNull() ?: 0.0
                }
            }
        }

        // ✅ Empty state
        if (expenseList.isEmpty()) {
            totalText.text = "No expenses yet"
        } else {
            totalText.text = "Total: R$total"
        }

        recycler.layoutManager = LinearLayoutManager(this)

        // ✅ UPDATED ADAPTER (WITH CONTEXT)
        recycler.adapter = ExpenseAdapter(this, expenseList)
    }
}