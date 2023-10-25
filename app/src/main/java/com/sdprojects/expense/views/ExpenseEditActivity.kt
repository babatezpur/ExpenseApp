package com.sdprojects.expense.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.sdprojects.expense.databinding.ActivityExpenseEditBinding
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.viewmodels.EditExpenseViewModel
import com.sdprojects.expense.viewmodels.EditExpenseViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ExpenseEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExpenseEditBinding
    private lateinit var viewModel : EditExpenseViewModel
    private var expenseModel : ExpenseModel? = null
    private var id : String? = null

    private val selectedDateTime: Calendar = Calendar.getInstance()

    private lateinit var categoryList : List<Category>

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

        viewModel.allCategories.observe(this){
            categoryList = it
        }

        binding.dropdownButton.setOnClickListener {
            showDropdownMenu()
        }

        binding.showDateTimePickerButton.setOnClickListener {
            showDateTimePicker()
        }

        binding.buttonDelete.setOnClickListener {
            showAlertDialogForDelete()
        }

        binding.buttonSave.setOnClickListener {
            showAlertDialogForSave()
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

    fun showAlertDialogForSave(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update and save expense")
        builder.setMessage("Are you sure you want to update this expense?")

        builder.setPositiveButton("YES") { dialog, which ->

            GlobalScope.launch {
                handleUpdate()
            }
            finish()
            return@setPositiveButton
        }

        builder.setNegativeButton("NO") { dialog, which ->
            return@setNegativeButton
        }

        builder.show()
    }

    fun showAlertDialogForDelete(){
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

    private fun showDropdownMenu() {
        val popupMenu = PopupMenu(this, binding.dropdownButton)
        categoryList.forEach { category ->
            popupMenu.menu.add(category.category)
        }
        popupMenu.menu.add("Other")

        popupMenu.setOnMenuItemClickListener { menuItem ->
            val selectedCategory = menuItem.title.toString()
            if (selectedCategory == "Other") {
                // Enable the EditText for custom category entry
                binding.editTextCategory.isEnabled = true
            } else {
                // Set the selected category in the EditText
                binding.editTextCategory.setText(selectedCategory)
                // Disable the EditText
                binding.editTextCategory.isEnabled = false
            }
            true
        }

        popupMenu.show()
    }

    private fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        val year = currentDate[Calendar.YEAR]
        val month = currentDate[Calendar.MONTH]
        val day = currentDate[Calendar.DAY_OF_MONTH]
        val hour = currentDate[Calendar.HOUR_OF_DAY]
        val minute = currentDate[Calendar.MINUTE]
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, day ->
                selectedDateTime[Calendar.YEAR] = year
                selectedDateTime[Calendar.MONTH] = month
                selectedDateTime[Calendar.DAY_OF_MONTH] = day
                val timePickerDialog = TimePickerDialog(this,
                    { timePicker, hour, minute ->
                        selectedDateTime[Calendar.HOUR_OF_DAY] = hour
                        selectedDateTime[Calendar.MINUTE] = minute
                        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        val formattedDateTime: String = sdf.format(selectedDateTime.time)
                        Toast.makeText(
                            this,
                            "Selected Date and Time: $formattedDateTime", Toast.LENGTH_LONG
                        ).show()
                        binding.textViewDate.text = formattedDateTime.split(' ')[0]
                        binding.textViewTime.text = formattedDateTime.split(' ')[1]
                    }, hour, minute, true
                )
                timePickerDialog.show()
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis() - 1000 // Restrict to future dates
        datePickerDialog.show()
    }

    suspend fun handleDelete(){
        id?.toInt()?.let { viewModel.deleteExpenseWithId(it) }
    }

    suspend fun handleUpdate(){

        if(binding.editTextCategory.text.isEmpty()|| binding.editTextExpenseAmount.text.isEmpty()){
            Toast.makeText(applicationContext, "Please enter Amount and Category correctly", Toast.LENGTH_LONG).show()
            return
        }
        if(binding.textViewDate.text.isEmpty() || binding.textViewTime.text.isEmpty()){
            Toast.makeText(applicationContext, "Please choose Date and Time by clicking the button", Toast.LENGTH_LONG).show()
            return
        }
        val expenseName = binding.editTextExpenseName.text.takeIf { it.isNotEmpty() }?.toString() ?: "Unnamed ${binding.editTextExpenseAmount.text}"
        val category = binding.editTextCategory.text.toString()
        val amount = binding.editTextExpenseAmount.text.toString()
        val date = "${binding.textViewDate.text}"
        val time = "${binding.textViewTime.text}"
        var note = ""
        if(!binding.editTextNote.text.isEmpty())
            note = binding.editTextNote.text.toString()

        viewModel.insertCategory(category)
        viewModel.updateExpense(
            id?.toInt()!!,
            expenseName,
            amount.toLong(),
            Category(category = category),
            date,
            time,
            note
        )
    }
}