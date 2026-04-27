package com.example.smartbudget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val context: Context,
    private val list: MutableList<Expense>
) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val amount: TextView = view.findViewById(R.id.itemAmount)
        val category: TextView = view.findViewById(R.id.itemCategory)
        val description: TextView = view.findViewById(R.id.itemDescription)
        val deleteBtn: Button = view.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.amount.text = "R${item.amount}"
        holder.category.text = item.category
        holder.description.text = item.description

        // 🔥 DELETE BUTTON LOGIC
        holder.deleteBtn.setOnClickListener {

            // Remove item from list
            list.removeAt(position)

            // Update SharedPreferences
            val sharedPref = context.getSharedPreferences("SmartBudget", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            val newData = list.joinToString(";") {
                "${it.amount},${it.category},${it.description}"
            }

            editor.putString("expenses", newData)
            editor.apply()

            // Refresh RecyclerView
            notifyDataSetChanged()

            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }
}