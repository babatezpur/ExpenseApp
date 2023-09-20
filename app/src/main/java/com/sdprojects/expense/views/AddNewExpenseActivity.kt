package com.sdprojects.expense.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.sdprojects.expense.R
import com.sdprojects.expense.viewmodels.NewExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*


// TODO : Category selector and date time textView filling and git

class AddNewExpenseActivity : AppCompatActivity() {

    private lateinit var showDateTimePickerButton: Button
    private val selectedDateTime: Calendar = Calendar.getInstance()
    private lateinit var categoryEditText : EditText
    private lateinit var dropdownButton : ImageButton

    private lateinit var categoryViewModel: NewExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_expense)

        showDateTimePickerButton = findViewById(R.id.showDateTimePickerButton)
        categoryEditText = findViewById(R.id.editTextCategory)
        dropdownButton = findViewById(R.id.dropdownButton)

        dropdownButton.setOnClickListener {
            showDropdownMenu()
        }

        showDateTimePickerButton.setOnClickListener {
           showDateTimePicker()
        }
    }

    private fun showDropdownMenu() {
        val popupMenu = PopupMenu(this, dropdownButton)
        val categories = categoryViewModel.allCategories
        categories?.forEach { category ->
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
                    }, hour, minute, false
                )
                timePickerDialog.show()
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis() - 1000 // Restrict to future dates
        datePickerDialog.show()
    }
}