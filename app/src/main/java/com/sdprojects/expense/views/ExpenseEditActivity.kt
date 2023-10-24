package com.sdprojects.expense.views

import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sdprojects.expense.databinding.ActivityExpenseEditBinding
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.viewmodels.EditExpenseViewModel
import com.sdprojects.expense.viewmodels.EditExpenseViewModelFactory
import com.sdprojects.expense.views.MainActivity.Companion.NEXT_SCREEN
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ExpenseEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExpenseEditBinding
    private lateinit var viewModel : EditExpenseViewModel
    private var expenseModel : ExpenseModel? = null
    private var id : String? = null

    companion object{
        const val NEXT_SCREEN="details_screen"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Edit your expense")

        id = intent.getStringExtra(NEXT_SCREEN)

        val viewModelFactory = EditExpenseViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditExpenseViewModel::class.java)
        GlobalScope.launch {
            if (id != null) {
                expenseModel = viewModel.getExpenseModel(id!!.toInt())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(expenseModel != null){
            setExpenseDetails()
        }

        binding.buttonDelete.setOnClickListener {
            showAlertDialog()

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

    fun showAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Expense")
        builder.setMessage("Are you sure you want to delete this expense?")

        builder.setPositiveButton("YES") { dialog, which ->

            GlobalScope.launch {
                handleDelete()
            }
            finish()
            return@setPositiveButton
        }

        builder.setNegativeButton("NO") { dialog, which ->
            return@setNegativeButton
        }

        builder.show()
    }

    suspend fun handleDelete(){
        id?.toInt()?.let { viewModel.deleteExpenseWithId(it) }
    }
}