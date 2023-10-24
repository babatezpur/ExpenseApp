package com.sdprojects.expense.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdprojects.expense.R
import com.sdprojects.expense.databinding.ActivityExpenseEditBinding
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.viewmodels.EditExpenseViewModel
import com.sdprojects.expense.viewmodels.EditExpenseViewModelFactory
import com.sdprojects.expense.viewmodels.MainActivityViewModel
import com.sdprojects.expense.viewmodels.MainActivityViewModelFactory
import com.sdprojects.expense.views.MainActivity.Companion.NEXT_SCREEN
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.properties.Delegates

class ExpenseEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExpenseEditBinding
    private lateinit var viewModel : EditExpenseViewModel
    private var expenseModel : ExpenseModel? = null

    companion object{
        const val NEXT_SCREEN="details_screen"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(NEXT_SCREEN)

        val viewModelFactory = EditExpenseViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditExpenseViewModel::class.java)
        GlobalScope.launch {
            if (id != null) {
                expenseModel = viewModel.getExpenseModel(id.toInt())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(expenseModel != null){
            setExpenseDetails()
        }
    }

    fun setExpenseDetails(){
        (binding.editTextExpenseName as TextView).text = expenseModel?.name
        (binding.editTextExpenseAmount as TextView).text = expenseModel?.amount.toString()
        (binding.editTextCategory as TextView).text = expenseModel?.category?.category
        (binding.textViewDate).text = expenseModel?.date
        (binding.textViewTime).text = expenseModel?.time
        (binding.editTextNote as TextView).text = expenseModel?.note
    }
}