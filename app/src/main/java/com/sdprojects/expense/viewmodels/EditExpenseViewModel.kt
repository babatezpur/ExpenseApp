package com.sdprojects.expense.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.repositories.ExpenseRepository
import com.sdprojects.expense.room.AppDatabase

class EditExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseRepository : ExpenseRepository

    init {
        val expenseDao = AppDatabase.getDatabase(application).expenseDao()
        expenseRepository = ExpenseRepository(expenseDao)
    }

    suspend fun getExpenseModel(id : Int) : ExpenseModel {
        return expenseRepository.getExpenseFromId(id)
    }


}