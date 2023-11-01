package com.sdprojects.expense.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdprojects.expense.R
import com.sdprojects.expense.models.Category

class ThisMonthStatsAdapter(private val dataList: List<Pair<Category, Long>>) : RecyclerView.Adapter<ThisMonthStatsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCategory: TextView = view.findViewById(R.id.textViewCategoryName)
        val textViewAmount: TextView = view.findViewById(R.id.textViewCategoryAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.this_month_expenses_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewCategory.text = dataList[position].first.category
        holder.textViewAmount.text = dataList[position].second.toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}