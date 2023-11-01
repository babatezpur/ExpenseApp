package com.sdprojects.expense.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.models.ExpenseModel

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense : ExpenseModel)

    @Query("select * from ExpenseModel order by date || ' ' || time desc limit 30")
    suspend fun getAllExpenses() : List<ExpenseModel>


    @Query("select * from ExpenseModel where date like :yearMonth || '-%'")
    suspend fun getSelectiveExpenses(yearMonth : String) : List<ExpenseModel>

    @Query("select * from ExpenseModel where id = :id")
    suspend fun getExpenseFromId(id : Int) : ExpenseModel

    @Query("delete from ExpenseModel where id = :id")
    suspend fun deleteExpenseWithId(id: Int)

    @Query("UPDATE ExpenseModel SET name = :name, amount = :amount, category = :category, date = :date, time = :time, note = :note WHERE id = :id")
    suspend fun updateExpense(id: Int, name: String, amount: Long, category: Category, date: String, time: String, note: String?)

    @Query("select category , SUM(amount) as total from ExpenseModel group by category order by total desc")
    suspend fun getExpenseByCategory() : List<ExpenseByCategory>
}

data class ExpenseByCategory(
    val category: Category,
    val total: Long
)