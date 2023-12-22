package com.example.calorease.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LunchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lunch: Lunch)

    @Update
    fun update(lunch: Lunch)

    @Delete
    fun delete(lunch: Lunch)

    @Query("SELECT * from lunch where tenantId = :userId order by id desc")
    fun getAllLunch(userId: String): LiveData<List<Lunch>>
}