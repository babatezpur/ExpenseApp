package com.sdprojects.expense.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sdprojects.expense.models.ExpenseModel

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense : ExpenseModel)

    @Query("select * from ExpenseModel")
    suspend fun getAllExpenses() : List<ExpenseModel>
}