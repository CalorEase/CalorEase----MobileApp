package com.example.calorease.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BreakfastDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(breakfast: Breakfast)

    @Update
    fun update(breakfast: Breakfast)

    @Delete
    fun delete(breakfast: Breakfast)

    @Query("SELECT * from breakfast where tenantId = :userId order by id desc")
    fun getAllBreakfast(userId: String): LiveData<List<Breakfast>>
}