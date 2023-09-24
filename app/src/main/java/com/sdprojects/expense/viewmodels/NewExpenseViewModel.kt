package com.sdprojects.expense.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.repositories.CategoryRepository
import com.sdprojects.expense.repositories.ExpenseRepository
import com.sdprojects.expense.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository
    val allCategories: MutableLiveData<List<Category>> = MutableLiveData()
    private val expenseRepository : ExpenseRepository

    init {
        val categoryDao = AppDatabase.getDatabase(application).categoryDao()
        val expenseDao = AppDatabase.getDatabase(application).expenseDao()
        categoryRepository = CategoryRepository(categoryDao)
        expenseRepository = ExpenseRepository(expenseDao)
        viewModelScope.launch(Dispatchers.IO) {
            // Call the suspend function getAllCategories within a coroutine
            val categories = categoryRepository.getAllCategories()
            // Assign the result to LiveData
            allCategories.postValue(categories)
        }
    }

    fun insertCategory(category: String) = viewModelScope.launch {
        categoryRepository.insertCategory(Category(category = category))
    }

    fun insertExpense(expenseName : String, amount : Long, category : Category, date : String, time : String, note :  String) = viewModelScope.launch{
        val expenseModel : ExpenseModel = ExpenseModel(name = expenseName, amount = amount, category = category, date = date, time = time, note = note )
        expenseRepository.insertExpense( expenseModel)
    }
}