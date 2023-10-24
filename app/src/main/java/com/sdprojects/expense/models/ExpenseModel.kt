package com.sdprojects.expense.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ExpenseModel( @PrimaryKey(autoGenerate = true) var id : Long = 0,
                         val name : String,
                         val amount : Long,
                         val category : Category,
                         val date : String,
                         val time : String,
                         val note : String?
) {
}