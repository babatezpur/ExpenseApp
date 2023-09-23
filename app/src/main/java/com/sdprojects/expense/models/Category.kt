package com.sdprojects.expense.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(@PrimaryKey(autoGenerate = true) val id : Int = 0, val category : String){
}
