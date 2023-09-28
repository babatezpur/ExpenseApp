package com.sdprojects.expense.repositories

import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.room.ExpenseDao

class ExpenseRepository(private val expenseDao : ExpenseDao) {

    suspend fun insertExpense(expense : ExpenseModel){
        expenseDao.insertExpense(expense)
    }

    suspend fun getAllExpenses() : List<ExpenseModel>{
        return expenseDao.getAllExpenses()
    }
}