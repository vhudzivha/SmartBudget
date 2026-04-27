package com.example.smartbudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class to display list of expenses in RecyclerView
class ExpenseAdapter(private val list: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    // ViewHolder holds references to item views
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val amount: TextView = view.findViewById(R.id.itemAmount)
        val category: TextView = view.findViewById(R.id.itemCategory)
        val description: TextView = view.findViewById(R.id.itemDescription)
    }

    // Inflate item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    // Number of items
    override fun getItemCount(): Int = list.size

    // Bind data to each item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        // Use string resource instead of hardcoding (best practice)
        holder.amount.text = holder.itemView.context.getString(
            R.string.amount_format,
            item.amount
        )

        holder.category.text = item.category
        holder.description.text = item.description
    }
}