package com.example.calorease.presentation.bottom_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.calorease.R
import com.example.calorease.data.database.CalorEaseDatabase
import com.example.calorease.databinding.FragmentHomeBinding
import com.example.calorease.ui.breakfast.BreakfastActivity
import com.example.calorease.ui.dinner.DinnerActivity
import com.example.calorease.ui.lunch.LunchActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: CalorEaseDatabase

    var carouselView: CarouselView? = null
    var imgArray: ArrayList<Int> = ArrayList()

    val auth = Firebase.auth
    val user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = CalorEaseDatabase.getDatabase(requireContext().applicationContext)

        binding.tvGreeting.text = "Hello! " + user?.displayName.toString()

//        imgArray.add(R.drawable.carousel1)
//        imgArray.add(R.drawable.carousel2)
//
//        carouselView = binding.carouselView
//        carouselView!!.pageCount = imgArray.size
//        carouselView!!.setImageListener(imageListener)

        binding.btnSarapan.setOnClickListener {
            val intent = Intent(context, BreakfastActivity::class.java)
            startActivity(intent)
        }

        binding.btnMakanSiang.setOnClickListener {
            val intent = Intent(context, LunchActivity::class.java)
            startActivity(intent)
        }

        binding.btnMakanMalam.setOnClickListener {
            val intent = Intent(context, DinnerActivity::class.java)
            startActivity(intent)
        }

    }

//    var imageListener = object : ImageListener{
//        override fun setImageForPosition(position: Int, imageView: ImageView?) {
//            imageView?.setImageResource(imgArray[position])
//        }
//
//    }


    companion object {

    }


}