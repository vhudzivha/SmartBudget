package com.example.smartbudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)

        val password = findViewById<EditText>(R.id.password)

        val loginBtn = findViewById<Button>(R.id.loginBtn)

        loginBtn.setOnClickListener {

            val userText = username.text.toString()

            val passText = password.text.toString()

            if (
                userText.isNotEmpty() &&
                passText.isNotEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        DashboardActivity::class.java
                    )
                )

            } else {

                Toast.makeText(
                    this,
                    "Enter username and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}