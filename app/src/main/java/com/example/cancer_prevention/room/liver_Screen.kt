package com.example.cancer_prevention.room

import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.cancer_prevention.R
import kotlinx.android.synthetic.main.activity_liver_screen.*
import java.text.SimpleDateFormat
import java.util.*

class liver_Screen : AppCompatActivity() {

    private val ONE_DAY = 24 * 60 * 60 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liver_screen)

        Locale.setDefault(Locale.KOREAN);

        var settings_Year: SharedPreferences = getSharedPreferences("no_smoke_start_Year", MODE_PRIVATE)

        var editor_Year: SharedPreferences.Editor = settings_Year.edit()

        var settings_Month: SharedPreferences = getSharedPreferences("no_smoke_start_Month", MODE_PRIVATE)

        var editor_Month: SharedPreferences.Editor = settings_Month.edit()

        var settings_Day: SharedPreferences = getSharedPreferences("no_smoke_start_Day", MODE_PRIVATE)

        var editor_Day: SharedPreferences.Editor = settings_Day.edit()

        val currentTime : Long = System.currentTimeMillis()

        val year = SimpleDateFormat("yyyy")
        val month = SimpleDateFormat("MM")
        val day = SimpleDateFormat("dd")
        val time = SimpleDateFormat("k:mm:ss")

        val mDate: Date = Date(currentTime)

        val getYear = year.format(mDate)
        val getMonth = month.format(mDate)
        val getDay = day.format(mDate)

        no_smoking_start.setOnClickListener {

            editor_Year.putString("Year", getYear.toString())
            editor_Year.commit()

            editor_Month.putString("Month", getMonth.toString())
            editor_Month.commit()

            editor_Day.putString("Day", getDay.toString())
            editor_Day.commit()

            edit_endDateBtn.setText(settings_Year.getString("Year","") + "년 " + settings_Month.getString("Month","") + "월 " + settings_Day.getString("Day","") + "일 ");

            try {
                edit_result.setText(getDday(Integer.parseInt(settings_Year.getString("Year","")), Integer.parseInt(settings_Month.getString("Month","")) - 1, Integer.parseInt(settings_Day.getString("Day",""))));
            }catch (e:Exception) {

            }

        }


        no_smoking_start_delete.setOnClickListener {
            editor_Year.remove("Year")
            editor_Year.commit()
            editor_Month.remove("Month")
            editor_Month.commit()
            editor_Day.remove("Day")
            editor_Day.commit()


            edit_endDateBtn.setText(settings_Year.getString("Year","") + "년 " + settings_Month.getString("Month","") + "월 " + settings_Day.getString("Day",""));

            try {
                edit_result.setText(getDday(Integer.parseInt(settings_Year.getString("Year","")), Integer.parseInt(settings_Month.getString("Month","")) - 1, Integer.parseInt(settings_Day.getString("Day",""))));
            }catch (e:Exception) {

            }

            edit_result.text = "금연을 시작하세요!"
        }


    }

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
}