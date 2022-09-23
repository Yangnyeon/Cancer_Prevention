package com.example.cancer_prevention.Retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.R
import com.example.cancer_prevention.R.layout.activity_cancer_retrofit
import com.example.cancer_prevention.databinding.ActivityCancerRetrofitBinding
import kotlinx.android.synthetic.main.activity_cancer_retrofit.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import retrofit2.Response

class Cancer_Retrofit : AppCompatActivity() {

        private lateinit var viewModel : Cancer_VIewModel

        private lateinit var binding : ActivityCancerRetrofitBinding

    private val myAdapter by lazy {
        Cancer_adapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cancer_retrofit)

        binding = ActivityCancerRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

// 어댑터 연결
       // binding.recyclerView.adapter = myAdapter
       // binding.recyclerView.layoutManager = LinearLayoutManager(this@Cancer_Retrofit, LinearLayoutManager.VERTICAL,false)

        val repository = Cancer_Repository()
        val viewModelFactory = Cancer_ViewModel_Factory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(Cancer_VIewModel::class.java)

        viewModel.myResponse.observe(this, Observer {
            if(it.isSuccessful){
                //myAdapter.setData(listOf(it.body()!!))
                val body = it.body()
                body?.let {
                    setAdapter(it.data as ArrayList<data>)
                }
            }
            else{
                Toast.makeText(this,it.code(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getPost(50,1,25)

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게


        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }

    }

    private fun setAdapter(Consult_List : MutableList<data>) {
        val mAdapter = Cancer_adapter(Consult_List)
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@Cancer_Retrofit)
    }


}