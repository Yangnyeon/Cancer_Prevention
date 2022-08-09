package com.example.cancer_prevention.room.Calendar

import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.databinding.ActivityCalendarRoomBinding
import com.example.cancer_prevention.room.OnItemClick
import com.example.cancer_prevention.room.Cigarette
import com.example.cancer_prevention.room.CigaretteAdapter
import com.example.cancer_prevention.room.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Calendar_Room : AppCompatActivity() , OnItemClick{

    private lateinit var binding : ActivityCalendarRoomBinding
    private val memoViewModel: TodoViewModel by viewModels() // 뷰모델 연결
    //private val adapter : CigaretteAdapter by lazy { CigaretteAdapter(this) } // 어댑터 선언
    private lateinit var adapter: CigaretteAdapter

    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalendarRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarRecyclerview.layoutManager = LinearLayoutManager(this@Calendar_Room, LinearLayoutManager.VERTICAL,false)
        adapter = CigaretteAdapter(this@Calendar_Room)
        binding.calendarRecyclerview.adapter = adapter
        //adapter.setHasStableIds(true)

        val currentTime : Long = System.currentTimeMillis()

        val year = SimpleDateFormat("yyyy")
        val month = SimpleDateFormat("MM")
        val day = SimpleDateFormat("dd")
        val time = SimpleDateFormat("k:mm:ss")

        val mDate: Date = Date(currentTime)

        val getYear = year.format(mDate)
        val getMonth = month.format(mDate)
        val getDay = day.format(mDate)
        val gettime = time.format(mDate)

        memoViewModel.readDateData(Integer.parseInt(getYear),Integer.parseInt(getMonth),Integer.parseInt(getDay)).observe(this@Calendar_Room, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

        binding.calendarDateText.text = Integer.parseInt(getYear).toString() + "/" + Integer.parseInt(getMonth) + "/" + Integer.parseInt(getDay)



        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            // 날짜 선택시 그 날의 정보 할당
            this.year = year
            this.month = month+1
            this.day = day

            binding.calendarDateText.text = "${this.year}/${this.month}/${this.day}"

            // 해당 날짜 데이터를 불러옴 (currentData 변경)
            memoViewModel.readDateData(this.year,this.month,this.day).observe(this@Calendar_Room, Observer {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            })

            /*
            memoViewModel.getAll().observe(this@Calendar_Room, Observer{
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            })
             */

        }


        memoViewModel.currentData.observe(this@Calendar_Room, Observer {
            adapter.setData(it)
            Log.d("test5", "onCreateView: gg")
        })

    }

    override fun deleteTodo(cigarette: Cigarette) {
        lifecycleScope.launch(Dispatchers.IO){
            memoViewModel.delete(cigarette)
        }
    }



}