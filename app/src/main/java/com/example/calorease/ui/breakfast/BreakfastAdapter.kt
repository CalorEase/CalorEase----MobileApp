package com.example.calorease.ui.breakfast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calorease.data.database.Breakfast
import com.example.calorease.databinding.ItemMenuBinding

class BreakfastAdapter : RecyclerView.Adapter<BreakfastAdapter.ListViewHolder>() {

    private val listBreakfast = ArrayList<Breakfast>()

    fun setListBreakfast(listBreakfast: List<Breakfast>){
        val diffCallback = BreakfastDiffCallback(this.listBreakfast, listBreakfast)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listBreakfast.clear()
        this.listBreakfast.addAll(listBreakfast)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bindng = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bindng)
    }

    override fun getItemCount(): Int {
        return listBreakfast.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBreakfast[position])
    }

    inner class ListViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(breakfast : Breakfast){
            with(binding){
                tvFoodName.text = breakfast.foodName.toString()
                tvPortion.text = breakfast.foodAmount.toString()
                tvCaloriesAmount.text = breakfast.foodCalories.toString()
                tvFoodFat.text = breakfast.foodFat.toString()
                tvFoodCarbo.text = breakfast.foodCarbo.toString()
                tvFoodProtein.text = breakfast.foodProtein.toString()
            }
        }
    }
}