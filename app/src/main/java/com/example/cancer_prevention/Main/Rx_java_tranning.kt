package com.example.cancer_prevention.Main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.cancer_prevention.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.operators.observable.ObservableAll
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import kotlinx.android.synthetic.main.activity_rx_java_tranning.*


import android.widget.TextView

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.Food.Food_Adapter
import com.example.cancer_prevention.Food.Food_model
import com.example.cancer_prevention.Nutrient.nutrient_adapter
import com.example.cancer_prevention.Nutrient.nutrient_model
import com.example.cancer_prevention.databinding.ActivityNutrientScreenBinding
import com.example.cancer_prevention.databinding.ActivityRxJavaTranningBinding
import com.example.cancer_prevention.databinding.FragmentNutritnerBinding
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Flowable.interval
import io.reactivex.rxjava3.core.Observable.interval
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromIterable
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import kotlinx.coroutines.*
import java.lang.Runnable
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.concurrent.timer


class Rx_java_tranning : AppCompatActivity() {


    lateinit var binding : ActivityRxJavaTranningBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRxJavaTranningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingAnimDialog = loading_screen(this@Rx_java_tranning)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()


        FirebaseFirestore.getInstance().collection("Food")
            .get()
            .addOnSuccessListener { result ->

                loadingAnimDialog.dismiss()

                for(document in result) {
                    val user = result.toObjects(Food_model::class.java)
                    binding.FoodRecyclerView.adapter = Food_Adapter(this,user)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "실패",Toast.LENGTH_SHORT).show()
            }

        binding.FoodRecyclerView.apply {
            layoutManager =  GridLayoutManager(this@Rx_java_tranning, 2)
        }

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }



    }



}