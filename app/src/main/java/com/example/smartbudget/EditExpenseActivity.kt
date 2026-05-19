package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_expense)

        val amount =
            findViewById<EditText>(R.id.editAmount)

        val category =
            findViewById<EditText>(R.id.editCategory)

        val description =
            findViewById<EditText>(R.id.editDescription)

        val updateBtn =
            findViewById<Button>(R.id.updateBtn)

        amount.setText(
            intent.getStringExtra("amount")
        )

        category.setText(
            intent.getStringExtra("category")
        )

        description.setText(
            intent.getStringExtra("description")
        )

        updateBtn.setOnClickListener {

            Toast.makeText(
                this,
                "Expense Updated",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )

            finish()
        }
    }
}