package com.example.cancer_prevention.room.Calendar

import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.example.cancer_prevention.databinding.ActivityCalendarRoomBinding
import com.example.cancer_prevention.room.*
import kotlinx.android.synthetic.main.activity_calendar_room.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Calendar_Room : AppCompatActivity() , OnItemClick{

    private lateinit var binding : ActivityCalendarRoomBinding
    private lateinit var memoViewModel: TodoViewModel  // 뷰모델 연결
    //private val adapter : CigaretteAdapter by lazy { CigaretteAdapter(this) } // 어댑터 선언
    private lateinit var adapter: CigaretteAdapter

    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalendarRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = CigaretteRepository(application)
        val viewModelFactory = Cigarette_Factory(repository)

        memoViewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

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

        calendarView.selectionManager = SingleSelectionManager(OnDaySelectedListener {
            val days: List<Calendar> = calendarView.getSelectedDates()
            var result = ""
            for (i in days.indices) {
                /*
                val week: String = SimpleDateFormat("EE").format(calendar.time)
                val day_full =
                    year.toString() + "년 " + (month + 1) + "월 " + day + "일 " + week + "요일"
                result +=  "$day_full"

                 */
                val calendar = days[i]
                val day = calendar[Calendar.DAY_OF_MONTH]
                val month = calendar[Calendar.MONTH]
                val year = calendar[Calendar.YEAR]

                binding.calendarDateText.text = "${year}/${month + 1}/${day}"

                memoViewModel.readDateData(year,month + 1,day).observe(this@Calendar_Room, Observer {
                    adapter.setList(it)
                    adapter.notifyDataSetChanged()
                })

            }
        })


        /*

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


         */



        memoViewModel.currentData.observe(this@Calendar_Room, Observer {
            adapter.setData(it)
            Log.d("test5", "onCreateView: gg")
        })

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }


    }

    override fun deleteTodo(cigarette: Cigarette) {
        lifecycleScope.launch(Dispatchers.IO){
            memoViewModel.delete(cigarette)
        }
    }



}