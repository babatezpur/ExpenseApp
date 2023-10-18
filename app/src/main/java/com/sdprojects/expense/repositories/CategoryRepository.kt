package com.sdprojects.expense.repositories

import com.sdprojects.expense.models.Category
import com.sdprojects.expense.room.CategoryDao

class CategoryRepository(private val categoryDao : CategoryDao) {

    suspend fun getAllCategories() : List<Category>{
        return categoryDao.getAllCategories()
    }

    suspend fun insertCategory(category : Category){
        val existingCategory = categoryDao.getCategoryByName(category.category)
        if (existingCategory == null) {
            categoryDao.insertCategory(category)
        }
    }
}