package com.example.cancer_prevention.room

import android.app.Application
import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cancer_prevention.databinding.TodoItemBinding

class CigaretteAdapter(listener: OnItemClick) : RecyclerView.Adapter<CigaretteAdapter.TodoViewHolder>() {

    private val mCallback = listener
    private val items = ArrayList<Cigarette>()

    private var memoList = emptyList<Cigarette>()

    lateinit var cigarette : Cigarette

    //디데이


    private val ONE_DAY = 24 * 60 * 60 * 1000

    //

    //서치뷰


    //



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    init {
        memoList = items
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(items[position], todoViewModel = TodoViewModel(Application()))
    }

    fun setList(cigarette: List<Cigarette>) {
        items.clear()
        items.addAll(cigarette)
    }


    inner class TodoViewHolder(private val binding: TodoItemBinding):RecyclerView.ViewHolder(binding.root){

        lateinit var cigarette : Cigarette

        fun bind(item: Cigarette, todoViewModel: TodoViewModel){


            binding.RoomData1.text = item.content
            binding.RoomData2.text = item.Year.toString() + "." + item.Month.toString() + "." + item.Day.toString() + "  " + item.time.toString()
            binding.roomDelete.setOnClickListener {
                mCallback.deleteTodo(item)
            }

            binding.RoomData3.text = getDday(item.Year, Integer.parseInt((item.Month - 1).toString()), item.Day)


            /*
            fun onOkButtonClicked(cigarette : Cigarette) {
                val updateMemo = Cigarette(item.check,item.content,item.Year,item.Month,item.Day)
                todoViewModel.updateMemo(updateMemo)
            }
            // 체크 리스너 초기화 해줘 중복 오류 방지
            binding.RoomData1.setOnCheckedChangeListener(null)

            // 메모 체크 시 체크 데이터 업데이트
            binding.RoomData1.setOnCheckedChangeListener { _, check ->
                if (check) {
                    /*
                     */
                    cigarette = Cigarette(true, item.content,
                        item.Year, item.Month, item.Day)
                    onOkButtonClicked(cigarette)
                }
                else {
                    /*
                    cigarette = Cigarette(false , item.content,
                        item.Year, item.Month, item.Day)
                    todoViewModel.updateMemo(cigarette)
                     */
                    cigarette = Cigarette(false, item.content,
                        item.Year, item.Month, item.Day)
                    onOkButtonClicked(cigarette)
                }
            }

             */


        }
    }



    fun setData(memo : List<Cigarette>){
        memoList = memo
        notifyDataSetChanged()
    }

    //디데이

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun getDday(
        mYear: Int,
        mMonthOfYear: Int,
        mDayOfMonth: Int
    ): String? {
        // D-day 설정
        val ddayCalendar = Calendar.getInstance()
        ddayCalendar[mYear, mMonthOfYear] = mDayOfMonth

        // D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
        val dday = ddayCalendar.timeInMillis / ONE_DAY
        val today = Calendar.getInstance().timeInMillis / ONE_DAY
        var result = dday - today

        // 출력 시 d-day 에 맞게 표시
        val strFormat: String
        if (result > 0) {
            strFormat = "D-%d"
        } else if (result == 0L) {
            strFormat = "오늘부터 시작?"
        } else {
            result *= -1
            strFormat = "금연한지 %d 일"
        }
        return String.format(strFormat, result)
    }

    /** @brief onPhotoDialog
     * @date 2016-02-18
     * @detail 디데이 값 계산
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun onCalculatorDate(dateEndY: Int, dateEndM: Int, dateEndD: Int): Int {
        return try {
            val today = Calendar.getInstance() //현재 오늘 날짜
            val dday = Calendar.getInstance()

            //시작일, 종료일 데이터 저장
            val calendar = Calendar.getInstance()
            val cyear = calendar[Calendar.YEAR]
            val cmonth = calendar[Calendar.MONTH]
            val cday = calendar[Calendar.DAY_OF_MONTH]
            today[cyear, cmonth] = cday
            dday[dateEndY, dateEndM] = dateEndD // D-day의 날짜를 입력합니다.
            val day = dday.timeInMillis / 86400000
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )
            val tday = today.timeInMillis / 86400000
            val count = tday - day // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            count.toInt() // 날짜는 하루 + 시켜줘야합니다.
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }


    //

}