package com.sdprojects.expense.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditExpenseViewModelFactory (private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditExpenseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        return super.create(modelClass)
    }
}