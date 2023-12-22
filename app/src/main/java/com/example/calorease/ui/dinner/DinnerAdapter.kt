package com.example.calorease.ui.dinner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.Dinner
import com.example.calorease.databinding.ItemMenuBinding
import com.example.calorease.ui.breakfast.BreakfastDiffCallback

class DinnerAdapter : RecyclerView.Adapter<DinnerAdapter.ListViewHolder>() {

    private val listDinner = ArrayList<Dinner>()

    fun setListDinner(listDinner: List<Dinner>){
        val diffCallback = DinnerDiffCallback(this.listDinner, listDinner)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listDinner.clear()
        this.listDinner.addAll(listDinner)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bindng = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bindng)
    }

    override fun getItemCount(): Int {
        return listDinner.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDinner[position])
    }

    inner class ListViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(dinner : Dinner){
            with(binding){
                tvFoodName.text = dinner.foodName.toString()
                tvPortion.text = dinner.foodAmount.toString()
                tvCaloriesAmount.text = dinner.foodCalories.toString()
                tvFoodFat.text = dinner.foodFat.toString()
                tvFoodCarbo.text = dinner.foodCarbo.toString()
                tvFoodProtein.text = dinner.foodProtein.toString()
            }
        }
    }
}