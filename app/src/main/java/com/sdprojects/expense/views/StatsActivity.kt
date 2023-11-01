package com.sdprojects.expense.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.sdprojects.expense.R

class StatsActivity : AppCompatActivity() {
    private lateinit var fragmentThisMonth : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        setTitle("Expense Statistics")

        fragmentThisMonth = ThisMonthExpense()

        val fragmentManager = supportFragmentManager

        val buttonThisMonth = findViewById<Button>(R.id.buttonThisMonth)

        buttonThisMonth.setOnClickListener {
            buttonThisMonth.isClickable = false
            buttonThisMonth.setTextColor(Color.GRAY)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.bottomFrameLayout, fragmentThisMonth)
            fragmentTransaction.commit()
        }
    }


}