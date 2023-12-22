package com.example.calorease.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Breakfast::class, Lunch::class, Dinner::class], version = 1)
abstract class CalorEaseDatabase : RoomDatabase(){
    abstract fun breakfastDao(): BreakfastDao
    abstract fun lunchDao(): LunchDao
    abstract fun dinnerDao(): DinnerDao

    companion object{
        @Volatile
        private var INSTANCE: CalorEaseDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CalorEaseDatabase{
            val instance = INSTANCE
            if (instance != null){
                return instance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalorEaseDatabase::class.java,
                    "calorease_database"
                ).build()

                INSTANCE = instance
                return instance
            }

//            return INSTANCE as FoodRoomDatabase
        }
    }
}