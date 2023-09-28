package com.sdprojects.expense.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.repositories.ExpenseRepository
import com.sdprojects.expense.room.AppDatabase
import kotlinx.coroutines.runBlocking

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val expenseRepository : ExpenseRepository

    init{
        val expenseDao = AppDatabase.getDatabase(application).expenseDao()
        expenseRepository = ExpenseRepository(expenseDao)
    }

    fun getExpenseList() : List<ExpenseModel> = runBlocking {
        return@runBlocking expenseRepository.getAllExpenses()
    }
}