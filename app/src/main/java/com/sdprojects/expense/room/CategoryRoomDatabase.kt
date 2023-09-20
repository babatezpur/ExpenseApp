package com.sdprojects.expense.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class CategoryRoomDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: CategoryRoomDatabase? = null

        fun getDatabase(context: Context): CategoryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryRoomDatabase::class.java,
                    "category_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}