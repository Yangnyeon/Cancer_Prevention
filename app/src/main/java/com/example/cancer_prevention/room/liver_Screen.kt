package com.example.cancer_prevention.room

import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import com.example.cancer_prevention.R
import kotlinx.android.synthetic.main.activity_community_holder.*
import kotlinx.android.synthetic.main.activity_liver_screen.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
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

            val comment_builder = AlertDialog.Builder(this@liver_Screen)


            // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
            val tvName = TextView(this@liver_Screen)
            tvName.text = "금연을 시작하시겠습니까?\n\n"

            val mLayout = LinearLayout(this@liver_Screen)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tvName)


            comment_builder.setView(mLayout)

            comment_builder.setTitle("금연 시작")
            comment_builder.setPositiveButton("확인") { dialog, which ->

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

                liver_changer.text = "금연 0 일차 : 오늘부터 금연시작!"

            }
            comment_builder.setNegativeButton("취소") { dialog, which ->

            }
            comment_builder.show()
        }


        edit_endDateBtn.setText(settings_Year.getString("Year","") + "년 " + settings_Month.getString("Month","") + "월 " + settings_Day.getString("Day","") + "일 ");

        try {
            edit_result.setText(getDday(Integer.parseInt(settings_Year.getString("Year","")), Integer.parseInt(settings_Month.getString("Month","")) - 1, Integer.parseInt(settings_Day.getString("Day",""))));
        }catch (e:Exception) {

        }


        no_smoking_start_delete.setOnClickListener {


            val comment_builder = AlertDialog.Builder(this@liver_Screen)


            // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
            val tvName = TextView(this@liver_Screen)
            tvName.text = "금연을 포기하시겠습니까..?\n\n"

            val mLayout = LinearLayout(this@liver_Screen)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tvName)


            comment_builder.setView(mLayout)

            comment_builder.setTitle("흡연 시작")
            comment_builder.setPositiveButton("확인") { dialog, which ->

                editor_Year.remove("Year")
                editor_Year.commit()
                editor_Month.remove("Month")
                editor_Month.commit()
                editor_Day.remove("Day")
                editor_Day.commit()


                edit_endDateBtn.setText(settings_Year.getString("Year","") + "년 " + settings_Month.getString("Month","") + "월 " + settings_Day.getString("Day",""));

                try {
                    edit_result.text = getDday(Integer.parseInt(settings_Year.getString("Year","")),
                        Integer.parseInt(settings_Month.getString("Month","")) - 1, Integer.parseInt(settings_Day.getString("Day","")))
                    //edit_result.setText(getDday(Integer.parseInt(settings_Year.getString("Year","")), Integer.parseInt(settings_Month.getString("Month","")) - 1, Integer.parseInt(settings_Day.getString("Day",""))));
                }catch (e:Exception) {

                }

                edit_result.text = "금연을 시작하세요!"
                liver_changer.text = "금연을 시작해보세요!"

            }
            comment_builder.setNegativeButton("취소") { dialog, which ->

            }
            comment_builder.show()

        }

        try {
            var count = getDday(Integer.parseInt(settings_Year.getString("Year","")),
                Integer.parseInt(settings_Month.getString("Month","")) - 1, Integer.parseInt(settings_Day.getString("Day","")))

            if(count!!.toInt() == 0) {
                liver_changer.text = "금연" + count!!.toInt() + " 일차 : 오늘부터 금연시작!"
            }
            else if(count!!.toInt() == 1) {
                liver_changer.text = "금연 1 일차 : 심장마비의 위험이 \n\n 떨어집니다!"
            } else if(count!!.toInt() == 2) {
                liver_changer.text = "금연 2 일차 : 신경 말단 부위가 니코틴이 사라져 \n\n 후각과 미각이 더 좋아지고 더 선명한 맛이 느껴\n\n집니다!"
            } else if(count!!.toInt() >= 3 && count!!.toInt() <= 13) {
                liver_changer.text = "금연" + count!!.toInt() + " 일차 : 신체의 니코틴 수치가 \n\n 감소합니다! 기관지가 이완되며 호흡이 쉬워지며 \n\n 폐활량도 증가합니다.!"
            } else if(count!!.toInt() >= 14 && count!!.toInt() <= 269) {
                liver_changer.text = "금연" + count!!.toInt() + " 일차 : 혈액순환이 좋아집니다! \n\n 걷기가 쉬워지며 \n\n폐 기능이 30% 증가합니다.!"
            } else if(count!!.toInt() >= 270 && count!!.toInt() <= 364) {
                liver_changer.text = "금연" + count!!.toInt() + " 일차 : 기침,피곤,산소 부족과 같은 증상이 모두 감소했습니다! \n\n 신체의 전반적인 에너지 수준도 높아졋습니다!"
            } else if(count!!.toInt() >= 365 && count!!.toInt() <= 1824) {
                liver_changer.text = "금연" + count!!.toInt() + " 일차 : 심장마비로 인한 사망 \n\n 위험이 흡연자에 비해 절반으로\n\n떨어졋습니다!"
            } else if(count!!.toInt() >= 1825) {
                liver_changer.text = "금연" + count!!.toInt() + " 일차 : 담배로 인해 좁아진 혈관이 다시 넓어지며 혈전 발생 위험이 줄어들어\n\n 심혈관질환의 위험이 감소했습니다!"
            } else {
                edit_endDateBtn.text = ""
                liver_changer.text = "금연을 시작해보세요!"
            }

        } catch(e: Exception) {

        }

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
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
            strFormat = "%d"
        } else {
            result *= -1
            strFormat = "%d"
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