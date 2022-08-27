package com.example.cancer_prevention.Nutrient

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.R
import com.example.cancer_prevention.Retrofit.Cancer_adapter
import com.example.cancer_prevention.Retrofit.data
import com.example.cancer_prevention.databinding.ActivityNutrientScreenBinding
import com.example.cancer_prevention.databinding.ActivityRoomBinding
import com.example.cancer_prevention.room.CigaretteAdapter
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import kotlinx.coroutines.launch
import retrofit2.Response
import androidx.activity.viewModels as viewModels1

class nutrient_screen : AppCompatActivity() {

    lateinit var binding : ActivityNutrientScreenBinding
    //lateinit var viewmodel : nutrient_viewmodel
    lateinit var adapter : nutrient_adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_nutrient_screen)

        binding = ActivityNutrientScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingAnimDialog = loading_screen(this@nutrient_screen)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        FirebaseFirestore.getInstance().collection("Nutrient")
            .get()
            .addOnSuccessListener {
                    result ->

                loadingAnimDialog.dismiss()

                for(document in result) {
                    val user = result.toObjects(nutrient_model::class.java)
                    binding.NutrientRecyclerView.adapter = nutrient_adapter(this,user)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "실패",Toast.LENGTH_SHORT).show()
            }

        binding.NutrientRecyclerView.apply {
            layoutManager =  GridLayoutManager(this@nutrient_screen, 2)
        }

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }

    }


}