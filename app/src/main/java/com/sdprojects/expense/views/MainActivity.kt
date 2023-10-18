package com.sdprojects.expense.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdprojects.expense.R
import com.sdprojects.expense.adapters.ExpenseAdapter
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.models.Month
import com.sdprojects.expense.viewmodels.MainActivityViewModel
import com.sdprojects.expense.viewmodels.MainActivityViewModelFactory
import com.sdprojects.expense.viewmodels.NewExpenseViewModel
import com.sdprojects.expense.viewmodels.NewExpenseViewModelFactory
import java.util.*

//add a button 'go' besides the year textview.
//check all categories coming same
class MainActivity : AppCompatActivity() {
    private lateinit var buttonAdd : Button
    private lateinit var buttonGo : Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ExpenseAdapter
    private lateinit var viewModel : MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd = findViewById(R.id.buttonAdd)
        buttonGo = findViewById(R.id.buttonGo)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddNewExpenseActivity::class.java)
            startActivity(intent)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val viewModelFactory = MainActivityViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)


        var expenses = viewModel.getExpenseList()
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter
        // this creates a vertical layout Manager


        //Populate the dropDown




    }

    override fun onResume() {
        super.onResume()
        var expenses = viewModel.getExpenseList()
        adapter.updateItems(expenses)

        val monthDropdown = findViewById<AutoCompleteTextView>(R.id.monthDropdown)
        val yearDropdown = findViewById<AutoCompleteTextView>(R.id.yearDropdown)

        val months = Month.values()
        val monthNames = months.map { it.monthName }

        Log.d("check123", monthNames.toString())

        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, monthNames)
        monthDropdown.setAdapter(monthAdapter)

        val years = listOf<String>("2023", "2024")
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, years)
        yearDropdown.setAdapter(yearAdapter)

        var selectedMonthIndex : String = ""
        var selectedYear : String = ""


        monthDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedMonth = monthDropdown.text.toString()
            selectedMonthIndex = (Month.valueOf(selectedMonth.uppercase(Locale.ROOT)).ordinal+1).toString() // Convert month name to enum and get its index
            Log.d("check123", selectedMonth)
            if (selectedMonthIndex.length == 1){
                selectedMonthIndex = "0"+selectedMonthIndex
            }
        }

        yearDropdown.setOnItemClickListener { _, _, position, _ ->
            selectedYear = years[position]
        }

        buttonGo.setOnClickListener {
            Log.d("check123", "${monthDropdown.length()}   ${yearDropdown.length()}")
            if(monthDropdown.length() == 0 && yearDropdown.length() == 0){
                expenses = viewModel.getExpenseList()
                adapter.updateItems(expenses)
            } else if(selectedMonthIndex.isNotEmpty() && selectedYear.isNotEmpty()) {
                Log.d("check123", "Entered the block: $selectedMonthIndex $selectedYear")
                expenses = viewModel.getSelectiveExpenses(selectedMonthIndex, selectedYear)
                adapter.updateItems(expenses)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }
}