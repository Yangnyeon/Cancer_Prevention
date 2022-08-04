package com.example.cancer_prevention.Nutrient

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cancer_prevention.Retrofit.Cancer_data_class
import com.example.cancer_prevention.databinding.ActivityNutrientScreenBinding
import com.example.cancer_prevention.room.Cigarette
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import retrofit2.Response

class nutrient_viewmodel(application: Application) : AndroidViewModel(application){

    lateinit var binding : ActivityNutrientScreenBinding

    lateinit var context : Context

    var nutrinet_data : MutableLiveData<ArrayList<nutrient_model>> = MutableLiveData<ArrayList<nutrient_model>>()

    val myResponse : MutableLiveData<Response<Cancer_data_class>> = MutableLiveData()




}