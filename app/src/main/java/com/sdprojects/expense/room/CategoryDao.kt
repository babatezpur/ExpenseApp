package com.sdprojects.expense.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdprojects.expense.models.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category : Category)

    @Query("select * from Category")
    suspend fun getAllCategories() : List<Category>

}