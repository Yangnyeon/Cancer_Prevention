package com.example.cancer_prevention.Retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cancer_prevention.R
import com.example.cancer_prevention.R.layout.activity_cancer_retrofit
import com.example.cancer_prevention.databinding.ActivityCancerRetrofitBinding
import kotlinx.android.synthetic.main.activity_cancer_retrofit.*

class Cancer_Retrofit : AppCompatActivity() {

    private lateinit var viewModel : Cancer_VIewModel

    private lateinit var binding : ActivityCancerRetrofitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cancer_retrofit)

        binding = ActivityCancerRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Cancer_Repository()
        val viewModelFactory = Cancer_ViewModel_Factory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(Cancer_VIewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer {
            if(it.isSuccessful){
                Log.d("Response",it.body()?.Cancer_number.toString())
                Log.d("Response",it.body()?.Cancer_number2.toString())
                Log.d("Response",it.body()?.Cancer_number3.toString())
                Log.d("Response",it.body()?.Cancer_number4.toString())
                Log.d("Response",it.body()?.Cancer_number5.toString())
                textView.text = it.body()?.Cancer_number!!
            }
            else{
                Log.d("Response",it.errorBody().toString())
                textView.text = it.code().toString()
            }
        })

    }
}