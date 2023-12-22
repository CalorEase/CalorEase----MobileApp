package com.example.calorease.ui.lunch

import androidx.recyclerview.widget.DiffUtil
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.Lunch

class LunchDiffCallback(private val oldLunchList: List<Lunch>, private val newLunchList:List<Lunch>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldLunchList.size

    override fun getNewListSize(): Int = newLunchList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldLunchList[oldItemPosition].id == newLunchList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBreakfast = oldLunchList[oldItemPosition]
        val newBreakfast = newLunchList[newItemPosition]
        return oldBreakfast.foodName == newBreakfast.foodName
    }
}