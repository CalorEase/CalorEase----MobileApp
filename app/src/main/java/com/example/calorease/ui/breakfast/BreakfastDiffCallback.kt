package com.example.calorease.ui.breakfast

import androidx.recyclerview.widget.DiffUtil
import com.example.calorease.data.database.Breakfast

class BreakfastDiffCallback(private val oldBreakfastList: List<Breakfast>, private val newBreakfastList:List<Breakfast>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldBreakfastList.size

    override fun getNewListSize(): Int = newBreakfastList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBreakfastList[oldItemPosition].id == newBreakfastList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBreakfast = oldBreakfastList[oldItemPosition]
        val newBreakfast = newBreakfastList[newItemPosition]
        return oldBreakfast.foodName == newBreakfast.foodName
    }
}