package com.sdprojects.expense.views

import android.os.Bundle
import android.provider.Settings.Global
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdprojects.expense.R
import com.sdprojects.expense.adapters.ThisMonthStatsAdapter
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.repositories.ExpenseRepository
import com.sdprojects.expense.room.AppDatabase
import com.sdprojects.expense.room.ExpenseDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThisMonthExpense.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThisMonthExpense : Fragment() {

    private lateinit var recyclerView: RecyclerView
    //private lateinit var expenseDao = ExpenseDao
    private var expenseDao: ExpenseDao? = null
    //private val expenseDao = context?.applicationContext?.let { AppDatabase.getDatabase(it).expenseDao() }
    private lateinit var expenseRepository: ExpenseRepository
    //private var expenseRepository : ExpenseRepository = ExpenseRepository(expenseDao!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        expenseDao = context?.applicationContext?.let { AppDatabase.getDatabase(it).expenseDao() }
        expenseRepository = ExpenseRepository(expenseDao!!)
        val view = inflater.inflate(R.layout.fragment_this_month_expense, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMonthExpense)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        GlobalScope.launch {
            recyclerView.adapter =  ThisMonthStatsAdapter(getListOfItems())
        }

        return view
    }


    //implement this function
    private suspend fun getListOfItems(): List<Pair<Category, Long>> {
        var list : List<Pair<Category, Long>>? = null
        return expenseRepository.getExpenseByCategory()

    }

}