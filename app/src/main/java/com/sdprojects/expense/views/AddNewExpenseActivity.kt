package com.sdprojects.expense.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.sdprojects.expense.R
import com.sdprojects.expense.models.Category
import com.sdprojects.expense.viewmodels.NewExpenseViewModel
import com.sdprojects.expense.viewmodels.NewExpenseViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


// TODO : Category selector and date time textView filling and git

class AddNewExpenseActivity : AppCompatActivity() {

    private lateinit var showDateTimePickerButton: Button
    private val selectedDateTime: Calendar = Calendar.getInstance()
    private lateinit var categoryEditText : EditText
    private lateinit var dropdownButton : ImageButton
    private lateinit var saveButton : Button
    private lateinit var editTextAmount: EditText
    private lateinit var editTextExpenseName : EditText

    private lateinit var textViewDate : TextView
    private lateinit var textViewTime : TextView

    private lateinit var categoryList : List<Category>

    private lateinit var newExpenseViewModel: NewExpenseViewModel

    private lateinit var editTextNote : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_expense)

        showDateTimePickerButton = findViewById(R.id.showDateTimePickerButton)
        categoryEditText = findViewById(R.id.editTextCategory)
        dropdownButton = findViewById(R.id.dropdownButton)
        saveButton = findViewById(R.id.buttonSave)

        textViewDate = findViewById(R.id.textViewDate)
        textViewTime = findViewById(R.id.textViewTime)

        editTextAmount = findViewById(R.id.editTextExpenseAmount)
        editTextExpenseName = findViewById(R.id.editTextExpenseName)

        val viewModelFactory = NewExpenseViewModelFactory(application)
        newExpenseViewModel = ViewModelProvider(this, viewModelFactory).get(NewExpenseViewModel::class.java)

        newExpenseViewModel.allCategories.observe(this){
            categoryList = it
        }

        dropdownButton.setOnClickListener {
            showDropdownMenu()
        }

        showDateTimePickerButton.setOnClickListener {
           showDateTimePicker()
        }

        saveButton.setOnClickListener {
            saveButtonClicked()
        }
    }

    fun saveButtonClicked(){
        if(categoryEditText.text.isEmpty() || editTextAmount.text.isEmpty()){
            Toast.makeText(applicationContext, "Please enter Amount and Category correctly", Toast.LENGTH_LONG).show()
            return
        }
        if(textViewDate.text.isEmpty() || textViewTime.text.isEmpty()){
            Toast.makeText(applicationContext, "Please choose Date and Time by clicking the button", Toast.LENGTH_LONG).show()
            return
        }
        val expenseName = editTextExpenseName.text.takeIf { it.isNotEmpty() }?.toString() ?: "Unnamed ${editTextAmount.text}"
        val category = categoryEditText.text.toString()
        val amount = editTextAmount.text
        val dateTime = "$textViewDate $textViewTime"
        val note = editTextNote

        newExpenseViewModel.insertCategory(category)
    }

    private fun showDropdownMenu() {
        val popupMenu = PopupMenu(this, dropdownButton)
        categoryList.forEach { category ->
            popupMenu.menu.add(category.toString())
        }
        popupMenu.menu.add("Other")

        popupMenu.setOnMenuItemClickListener { menuItem ->
            val selectedCategory = menuItem.title.toString()
            if (selectedCategory == "Other") {
                // Enable the EditText for custom category entry
                categoryEditText.isEnabled = true
            } else {
                // Set the selected category in the EditText
                categoryEditText.setText(selectedCategory)
                // Disable the EditText
                categoryEditText.isEnabled = false
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
                        textViewDate.text = formattedDateTime.split(' ')[0]
                        textViewTime.text = formattedDateTime.split(' ')[1]
                    }, hour, minute, true
                )
                timePickerDialog.show()
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis() - 1000 // Restrict to future dates
        datePickerDialog.show()
    }


}