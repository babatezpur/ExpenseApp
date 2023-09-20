package com.sdprojects.expense.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(@PrimaryKey val id : Int, val category : String){
}
