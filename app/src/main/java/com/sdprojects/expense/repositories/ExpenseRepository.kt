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

    suspend fun getSelectiveExpenses(yearMonth : String) : List<ExpenseModel>{
        return expenseDao.getSelectiveExpenses(yearMonth)
    }

    suspend fun getExpenseFromId(id : Int) : ExpenseModel{
        return expenseDao.getExpenseFromId(id)
    }

    suspend fun deleteExpenseWithId(id : Int) {
        expenseDao.deleteExpenseWithId(id)
    }

    suspend fun updateExpense(id : Int, model : ExpenseModel) {
        expenseDao.updateExpense(id,
                model.name,
                model.amount,
                model.category,
                model.date,
                model.time,
                model.note
            )
    }
}