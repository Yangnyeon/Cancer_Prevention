package com.example.cancer_prevention.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cancer_prevention.databinding.ActivityRoomBinding
import com.example.cancer_prevention.room.Calendar.Calendar_Room
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Room_Activity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityRoomBinding
    private val model: TodoViewModel by viewModels()
    private lateinit var adapter: CigaretteAdapter

    var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

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


                model.insert(Cigarette(false,"흡연..", Integer.parseInt(getYear), Integer.parseInt(getMonth), Integer.parseInt(getDay), gettime.toString()))
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

        //

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




}