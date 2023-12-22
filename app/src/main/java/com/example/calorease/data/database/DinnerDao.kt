package com.example.calorease.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DinnerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dinner: Dinner)

    @Update
    fun update(dinner: Dinner)

    @Delete
    fun delete(dinner: Dinner)

    @Query("SELECT * from dinner where tenantId = :userId order by id desc")
    fun getAllDinner(userId: String): LiveData<List<Dinner>>
}