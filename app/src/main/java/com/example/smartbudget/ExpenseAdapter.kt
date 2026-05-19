package com.example.smartbudget

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val context: Context,
    private val list: MutableList<Expense>
) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val amount: TextView =
            view.findViewById(R.id.itemAmount)

        val category: TextView =
            view.findViewById(R.id.itemCategory)

        val description: TextView =
            view.findViewById(R.id.itemDescription)

        val date: TextView =
            view.findViewById(R.id.dateText)

        val deleteBtn: Button =
            view.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_expense,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.amount.text =
            "R${item.amount}"

        holder.category.text =
            item.category

        holder.description.text =
            item.description

        holder.date.text =
            item.date

        // Delete Expense

        holder.deleteBtn.setOnClickListener {

            list.removeAt(position)

            notifyItemRemoved(position)

            saveUpdatedExpenses()
        }

        // Open Edit Screen

        holder.itemView.setOnClickListener {

            val intent =
                Intent(
                    context,
                    EditExpenseActivity::class.java
                )

            intent.putExtra(
                "amount",
                item.amount
            )

            intent.putExtra(
                "category",
                item.category
            )

            intent.putExtra(
                "description",
                item.description
            )

            context.startActivity(intent)
        }
    }

    private fun saveUpdatedExpenses() {

        val sharedPref =
            context.getSharedPreferences(
                "SmartBudget",
                Context.MODE_PRIVATE
            )

        val editor =
            sharedPref.edit()

        var newData = ""

        for (expense in list) {

            newData +=
                "${expense.amount}," +
                        "${expense.category}," +
                        "${expense.description}," +
                        "${expense.date};"
        }

        editor.putString(
            "expenses",
            newData
        )

        editor.apply()
    }
}