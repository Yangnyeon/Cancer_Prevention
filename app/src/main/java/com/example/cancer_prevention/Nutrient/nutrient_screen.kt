package com.example.cancer_prevention.Nutrient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.R
import com.example.cancer_prevention.Retrofit.Cancer_adapter
import com.example.cancer_prevention.Retrofit.data
import com.example.cancer_prevention.databinding.ActivityNutrientScreenBinding
import com.example.cancer_prevention.databinding.ActivityRoomBinding
import com.example.cancer_prevention.room.CigaretteAdapter
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.firestore.FirebaseFirestore
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
/*
        viewmodel = ViewModelProvider(this).get(nutrient_viewmodel::class.java)


 */
        /*
        initRecyclerView()

         */

        /*

        FirebaseFirestore.getInstance().collection("Nutrient")
            .get()
            .addOnSuccessListener {
                    result ->
                for(document in result) {
                    val user = result.toObjects(nutrient_model::class.java)
                    binding.NutrientRecyclerView.adapter = nutrient_adapter(this,user)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "실패",Toast.LENGTH_SHORT).show()
            }

         */


        binding.NutrientRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@nutrient_screen)
        }


        /*
        viewmodel.nutrinet_data.observe(this, Observer {
            /*
            initRecyclerView()
            adapter.setData(it)
            adapter.notifyDataSetChanged()
             */
            setAdapter(it)
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        })

         */



    }






}