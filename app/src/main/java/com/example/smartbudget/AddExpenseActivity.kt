package com.example.smartbudget

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val amount = findViewById<EditText>(R.id.amount)
        val category = findViewById<EditText>(R.id.category)
        val description = findViewById<EditText>(R.id.description)
        val saveBtn = findViewById<Button>(R.id.saveBtn)

        saveBtn.setOnClickListener {

            val amountText = amount.text.toString()
            val categoryText = category.text.toString()
            val descriptionText = description.text.toString()

            if (amountText.isNotEmpty() && categoryText.isNotEmpty()) {

                if (amountText.toDoubleOrNull() == null) {
                    Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val sharedPref = getSharedPreferences("SmartBudget", MODE_PRIVATE)

                val existing = sharedPref.getString("expenses", "") ?: ""
                val newData = "$amountText,$categoryText,$descriptionText;"

                sharedPref.edit().putString("expenses", existing + newData).apply()

                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                // 🔥 VERY IMPORTANT: GO BACK TO DASHBOARD
                finish()

            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}