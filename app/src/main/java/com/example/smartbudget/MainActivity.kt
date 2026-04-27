package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// This activity handles user login functionality
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Link layout to activity
        setContentView(R.layout.activity_main)

        // Get UI components
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        // Log app start
        Log.d("SmartBudget", "MainActivity started")

        // Handle login click
        loginBtn.setOnClickListener {

            val userText = username.text.toString()
            val passText = password.text.toString()

            Log.d("SmartBudget", "Login attempt: $userText")

            // Validate input
            if (userText.isNotEmpty() && passText.isNotEmpty()) {

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                // Move to dashboard
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}