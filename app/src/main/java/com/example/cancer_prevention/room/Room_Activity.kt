package com.example.cancer_prevention.room

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cancer_prevention.databinding.ActivityRoomBinding
import com.example.cancer_prevention.room.Calendar.Calendar_Room
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Room_Activity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityRoomBinding
   // private val model: TodoViewModel by viewModels()

    private lateinit var model : TodoViewModel

    private lateinit var adapter: CigaretteAdapter


    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        val repository = CigaretteRepository(application)
        val viewModelFactory = Cigarette_Factory(repository)

        model = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

        model.getAll().observe(this@Room_Activity, Observer{
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

        binding.btnAdd.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                val currentTime : Long = System.currentTimeMillis()

                //val year = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
                val year = SimpleDateFormat("yyyy")
                val month = SimpleDateFormat("MM")
                val day = SimpleDateFormat("dd")
                val time = SimpleDateFormat("k:mm:ss")

                val mDate: Date = Date(currentTime)

                val getYear = year.format(mDate)
                val getMonth = month.format(mDate)
                val getDay = day.format(mDate)
                val gettime = time.format(mDate)


                model.insert(Cigarette(false,"하하", Integer.parseInt(getYear), Integer.parseInt(getMonth), Integer.parseInt(getDay), gettime.toString()))
            }
        }

        binding.calendar.setOnClickListener {
            startActivity(Intent(this@Room_Activity, Calendar_Room::class.java))
        }


        model.getAll().observe(this@Room_Activity, Observer{
            for(i in it) {
                cigarette_count.text = "흡연횟수 : " + it.size.toString()
                //cigarette_count.append(i.Year.toString() + "  ")
            }
        })


        cigarette_count.text = count.toString()


        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }




    }

    private fun initRecyclerView(){
        binding.recyclerViewTodo.layoutManager = LinearLayoutManager(this)
        adapter = CigaretteAdapter(this)
        binding.recyclerViewTodo.adapter = adapter
    }

    override fun deleteTodo(cigarette: Cigarette) {
        lifecycleScope.launch(Dispatchers.IO){
            model.delete(cigarette)
        }
    }

    //

    private var searchViewTextListener: androidx.appcompat.widget.SearchView.OnQueryTextListener =
        object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                /*
                val mAdapter = CigaretteAdapter(this@Room_Activity)
                mAdapter.filter.filter(s)
                Log.d(TAG, "SearchVies Text is changed : $s")

                 */


                if(s != null) {
                    searchDatabase(s)
                } else if(s == null) {
                    model.getAll().observe(this@Room_Activity, Observer{
                        adapter.setData(it)
                        adapter.notifyDataSetChanged()
                    })
                }
                return true
            }
        }

    //

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        model.searchDatabase(searchQuery).observe(this, {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        })

        /*

        model.getAll().observe(this@Room_Activity, Observer{
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

         */
    }




}