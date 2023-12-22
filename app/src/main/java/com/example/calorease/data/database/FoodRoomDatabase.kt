package com.example.calorease.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Breakfast::class, Lunch::class], version = 3)
abstract class FoodRoomDatabase : RoomDatabase(){
    abstract fun breakfastDao(): BreakfastDao
    abstract fun lunchDao(): LunchDao

    companion object{
        @Volatile
        private var INSTANCE: FoodRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FoodRoomDatabase{
            val instance = INSTANCE
            if (instance != null){
                return instance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodRoomDatabase::class.java,
                    "calorease_db"
                ).build()

                INSTANCE = instance
                return instance
            }

//            return INSTANCE as FoodRoomDatabase
        }
    }
}