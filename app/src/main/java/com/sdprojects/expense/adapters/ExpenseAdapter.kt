package com.sdprojects.expense.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdprojects.expense.R
import com.sdprojects.expense.models.ExpenseModel

class ExpenseAdapter(private val listExpense : List<ExpenseModel>) : RecyclerView.Adapter<ExpenseAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseName : TextView = itemView.findViewById(R.id.name)
        val expenseAmount : TextView = itemView.findViewById(R.id.amount)
        val expenseCategory : TextView = itemView.findViewById(R.id.category)
        val expenseDate : TextView = itemView.findViewById(R.id.datetime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.expense_cardview,
            parent,
            false
        )
        return CustomViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        return listExpense.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val expense = listExpense[position]
        holder.expenseName.text = expense.name
        holder.expenseAmount.text = expense.amount.toString()
        holder.expenseCategory.text = expense.category.toString()
        holder.expenseDate.text = "Time ${expense.time} \nDate ${expense.date}"
    }
}