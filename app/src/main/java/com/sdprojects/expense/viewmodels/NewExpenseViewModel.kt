package com.sdprojects.expense.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.repositories.CategoryRepository
import com.sdprojects.expense.room.CategoryDao
import com.sdprojects.expense.room.CategoryRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository
    val allCategories : List<Category>
    //val allCategories: LiveData<List<Category>>

    init {
        val categoryDao = CategoryRoomDatabase.getDatabase(application).categoryDao()
        categoryRepository = CategoryRepository(categoryDao)
        allCategories = categoryRepository.getAllCategories()
    }

    fun insertCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.insertCategory(category)
    }
}