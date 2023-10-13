package com.sdprojects.expense.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sdprojects.expense.models.ExpenseModel

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense : ExpenseModel)

    @Query("select * from ExpenseModel order by date || ' ' || time desc limit 30")
    suspend fun getAllExpenses() : List<ExpenseModel>


    @Query("select * from ExpenseModel where date like :yearMonth || '-%'")
    suspend fun getSelectiveExpenses(yearMonth : String) : List<ExpenseModel>
}