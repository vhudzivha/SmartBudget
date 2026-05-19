package com.example.smartbudget

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var receiptImage: ImageView

    private var imageUri: Uri? = null

    private val PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_expense)

        val amount =
            findViewById<EditText>(R.id.amount)

        val description =
            findViewById<EditText>(R.id.description)

        val categorySpinner =
            findViewById<Spinner>(R.id.categorySpinner)

        val saveBtn =
            findViewById<Button>(R.id.saveBtn)

        val uploadBtn =
            findViewById<Button>(R.id.uploadBtn)

        receiptImage =
            findViewById(R.id.receiptImage)

        val categories = arrayOf(
            "Food",
            "Transport",
            "Entertainment",
            "School",
            "Petrol",
            "Savings",
            "Other"
        )

        val adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
            )

        categorySpinner.adapter = adapter

        // Upload Receipt

        uploadBtn.setOnClickListener {

            val gallery =
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )

            startActivityForResult(
                gallery,
                PICK_IMAGE
            )
        }

        saveBtn.setOnClickListener {

            val amountText =
                amount.text.toString()

            val descriptionText =
                description.text.toString()

            val categoryText =
                categorySpinner.selectedItem.toString()

            if (
                amountText.isEmpty() ||
                descriptionText.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val currentDate =
                    SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale.getDefault()
                    ).format(Date())

                val sharedPref =
                    getSharedPreferences(
                        "SmartBudget",
                        MODE_PRIVATE
                    )

                val existing =
                    sharedPref.getString(
                        "expenses",
                        ""
                    )

                val newData =
                    "$amountText,$categoryText,$descriptionText,$currentDate;"

                sharedPref.edit()
                    .putString(
                        "expenses",
                        existing + newData
                    )
                    .apply()

                Toast.makeText(
                    this,
                    "Expense Saved",
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

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == PICK_IMAGE &&
            resultCode == RESULT_OK &&
            data != null
        ) {

            imageUri = data.data

            receiptImage.setImageURI(imageUri)
        }
    }
}