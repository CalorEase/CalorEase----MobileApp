package com.example.calorease.ui.lunch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.Lunch
import com.example.calorease.databinding.ItemMenuBinding
import com.example.calorease.ui.breakfast.BreakfastDiffCallback

class LunchAdapter : RecyclerView.Adapter<LunchAdapter.ListViewHolder>() {

    private val listLunch = ArrayList<Lunch>()

    fun setListLunch(listLunch: List<Lunch>){
        val diffCallback = LunchDiffCallback(this.listLunch, listLunch)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listLunch.clear()
        this.listLunch.addAll(listLunch)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bindng = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bindng)
    }

    override fun getItemCount(): Int {
        return listLunch.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listLunch[position])
    }

    inner class ListViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(lunch : Lunch){
            with(binding){
                tvFoodName.text = lunch.foodName.toString()
                tvPortion.text = lunch.foodAmount.toString()
                tvCaloriesAmount.text = lunch.foodCalories.toString()
                tvFoodFat.text = lunch.foodFat.toString()
                tvFoodCarbo.text = lunch.foodCarbo.toString()
                tvFoodProtein.text = lunch.foodProtein.toString()
            }
        }
    }
}