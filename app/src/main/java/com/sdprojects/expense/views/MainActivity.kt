package com.sdprojects.expense.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdprojects.expense.R
import com.sdprojects.expense.adapters.ExpenseAdapter
import com.sdprojects.expense.models.ExpenseModel
import com.sdprojects.expense.viewmodels.MainActivityViewModel
import com.sdprojects.expense.viewmodels.MainActivityViewModelFactory
import com.sdprojects.expense.viewmodels.NewExpenseViewModel
import com.sdprojects.expense.viewmodels.NewExpenseViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var buttonAdd : Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ExpenseAdapter
    private lateinit var viewModel : MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd = findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddNewExpenseActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val viewModelFactory = MainActivityViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)


        var expenses = viewModel.getExpenseList()
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter
        // this creates a vertical layout Manager

    }

    override fun onResume() {
        super.onResume()
        var expenses = viewModel.getExpenseList()
        adapter.updateItems(expenses)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }
}