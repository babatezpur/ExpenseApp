package com.sdprojects.expense.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdprojects.expense.models.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category : Category)

    @Query("SELECT * FROM Category WHERE category = :categoryName")
    suspend fun getCategoryByName(categoryName: String): Category?

    @Query("select * from Category group by category")
    suspend fun getAllCategories() : List<Category>

}