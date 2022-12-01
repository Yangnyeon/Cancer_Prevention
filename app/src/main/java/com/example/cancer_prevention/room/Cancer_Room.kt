package com.example.cancer_prevention.room

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.R
import com.example.cancer_prevention.Retrofit.*
import com.example.cancer_prevention.databinding.ActivityCancerRoomBinding
import com.example.cancer_prevention.databinding.ActivityRoomBinding
import kotlinx.android.synthetic.main.activity_cancer_room.*

class Cancer_Room : AppCompatActivity() {

    private lateinit var binding: ActivityCancerRoomBinding
    private lateinit var viewModel : Cancer_VIewModel
    lateinit var adapter: Cancer_adapter

    var Cancer_list: ArrayList<data> = java.util.ArrayList<data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancerRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mAdapter = Cancer_adapter(Cancer_list)
        binding.CancerRoomRecyclerView.adapter = mAdapter
        binding.CancerRoomRecyclerView.layoutManager = LinearLayoutManager(this@Cancer_Room)

        val repository = Cancer_Repository()
        val viewModelFactory = Cancer_ViewModel_Factory(repository)

        viewModel = ViewModelProvider(this,viewModelFactory).get(Cancer_VIewModel::class.java)

        viewModel.getPost(50,1,25)

        viewModel.myResponse.observe(this, Observer {
            if(it.isSuccessful){
                //myAdapter.setData(listOf(it.body()!!))
                val body = it.body()
                body?.let {
                    setAdapter(it.data as ArrayList<data>)
                    binding.CancerSearchview.setOnQueryTextListener(searchViewTextListener)
                }
            }
            else{
                Toast.makeText(this,it.code(), Toast.LENGTH_SHORT).show()
            }
        })





        /*
        viewModel.getAll().observe(this@Room_Activity, Observer{
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

         */
    }

    private fun setAdapter(Consult_List : MutableList<data>) {
        val mAdapter = Cancer_adapter(Consult_List)
        binding.CancerRoomRecyclerView.adapter = mAdapter
        binding.CancerRoomRecyclerView.layoutManager = LinearLayoutManager(this@Cancer_Room)

        binding.CancerSearchview.setOnQueryTextListener(searchViewTextListener)
    }

    private var searchViewTextListener: androidx.appcompat.widget.SearchView.OnQueryTextListener =
        object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                val mAdapter = Cancer_adapter(Cancer_list)
                mAdapter.filter.filter(s).toString()
                Log.d(TAG, "SearchVies Text is changed : $s")
                return false
            }
        }

}