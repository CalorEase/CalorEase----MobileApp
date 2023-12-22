package com.example.calorease.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "dinner")
@Parcelize
data class Dinner(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "tenantId")
    var tenantId: String? = null,

    @ColumnInfo(name = "username")
    var username : String? = null,

    @ColumnInfo(name = "foodName")
    var foodName: String? = null,

    @ColumnInfo(name = "foodAmount")
    var foodAmount: String? = null,

    @ColumnInfo(name = "foodCalories")
    var foodCalories: String? = null,

    @ColumnInfo(name = "foodFat")
    var foodFat: String? = null,

    @ColumnInfo(name = "foodCarbo")
    var foodCarbo: String? = null,

    @ColumnInfo(name = "foodProtein")
    var foodProtein: String? = null,
): Parcelable