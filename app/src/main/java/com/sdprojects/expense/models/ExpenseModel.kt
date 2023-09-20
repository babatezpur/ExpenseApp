package com.sdprojects.expense.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ExpenseModel( @PrimaryKey(autoGenerate = true) var id : Long? = null,
                         val title : String,
                         val amount : Float,
                         //TODO: Add category structure
                         val Date : Date,

) {
}