package com.sdprojects.expense.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.repositories.CategoryRepository
import com.sdprojects.expense.repositories.ExpenseRepository
import com.sdprojects.expense.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseRepository : ExpenseRepository
    private val categoryRepository: CategoryRepository
    val allCategories: MutableLiveData<List<Category>> = MutableLiveData()

    init {
        val categoryDao = AppDatabase.getDatabase(application).categoryDao()
        val expenseDao = AppDatabase.getDatabase(application).expenseDao()
        expenseRepository = ExpenseRepository(expenseDao)
        categoryRepository = CategoryRepository(categoryDao)
        viewModelScope.launch(Dispatchers.IO) {
            // Call the suspend function getAllCategories within a coroutine
            val categories = categoryRepository.getAllCategories()
            // Assign the result to LiveData
            allCategories.postValue(categories)
        }



    }

    suspend fun getExpenseModel(id : Int) : ExpenseModel {
        return expenseRepository.getExpenseFromId(id)
    }

    suspend fun deleteExpenseWithId(id : Int) {
        expenseRepository.deleteExpenseWithId(id)
    }

    fun insertCategory(category: String) = viewModelScope.launch {
        categoryRepository.insertCategory(Category(category = category))
    }

    fun updateExpense(id : Int, expenseName : String, amount : Long, category : Category, date : String, time : String, note :  String) = viewModelScope.launch{
        val expenseModel  = ExpenseModel(name = expenseName, amount = amount, category = category, date = date, time = time, note = note )
        expenseRepository.updateExpense( id, expenseModel)
    }


}