package com.example.smartbudget

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// This activity allows the user to add a new expense
class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Link layout to activity
        setContentView(R.layout.activity_add_expense)

        // Get UI components
        val amount = findViewById<EditText>(R.id.amount)
        val category = findViewById<EditText>(R.id.category)
        val description = findViewById<EditText>(R.id.description)
        val saveBtn = findViewById<Button>(R.id.saveBtn)

        // Log screen open
        Log.d("SmartBudget", "AddExpenseActivity opened")

        // When Save button is clicked
        saveBtn.setOnClickListener {

            val amountText = amount.text.toString()
            val categoryText = category.text.toString()
            val descriptionText = description.text.toString()

            Log.d("SmartBudget", "Save clicked: $amountText, $categoryText")

            // Validate input
            if (amountText.isNotEmpty() && categoryText.isNotEmpty()) {

                // Check if amount is a number
                if (amountText.toDoubleOrNull() == null) {
                    Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Save data using SharedPreferences
                val sharedPref = getSharedPreferences("SmartBudget", MODE_PRIVATE)

                val existing = sharedPref.getString("expenses", "") ?: ""
                val newData = "$amountText,$categoryText,$descriptionText;"

                sharedPref.edit().putString("expenses", existing + newData).apply()

                Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()

                Log.d("SmartBudget", "Expense saved successfully")

                // Clear fields
                amount.text.clear()
                category.text.clear()
                description.text.clear()

            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}