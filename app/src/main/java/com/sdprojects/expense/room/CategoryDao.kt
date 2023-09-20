package com.sdprojects.expense.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdprojects.expense.models.Category


interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category : Category)

    @Query("select * from Category")
    fun getAllCategories() : List<Category>

}