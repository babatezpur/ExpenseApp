package com.sdprojects.expense.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.repositories.CategoryRepository
import com.sdprojects.expense.room.CategoryDao
import com.sdprojects.expense.room.CategoryRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository
    val allCategories: MutableLiveData<List<Category>> = MutableLiveData()

    init {
        val categoryDao = CategoryRoomDatabase.getDatabase(application).categoryDao()
        categoryRepository = CategoryRepository(categoryDao)
        viewModelScope.launch(Dispatchers.IO) {
            // Call the suspend function getAllCategories within a coroutine
            val categories = categoryRepository.getAllCategories()
            // Assign the result to LiveData
            allCategories.postValue(categories)
        }
    }

    fun insertCategory(category: String) = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.insertCategory(Category(category = category))
    }
}