package com.example.calorease.ui.dinner

import androidx.recyclerview.widget.DiffUtil
import com.example.calorease.data.database.Dinner

class DinnerDiffCallback(private val oldDinnerList: List<Dinner>, private val newDinnerList:List<Dinner>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldDinnerList.size

    override fun getNewListSize(): Int = newDinnerList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDinnerList[oldItemPosition].id == newDinnerList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDinnerList = oldDinnerList[oldItemPosition]
        val newDinnerList = newDinnerList[newItemPosition]
        return oldDinnerList.foodName == newDinnerList.foodName
    }
}