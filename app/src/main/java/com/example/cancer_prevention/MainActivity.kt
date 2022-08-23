package com.example.cancer_prevention

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.cancer_prevention.Community.Community_Fragment
import com.example.cancer_prevention.Introduce.Introduce_Screen
import com.example.cancer_prevention.Main.Intro_Screen
import com.example.cancer_prevention.Main.Main_Fragment
import com.example.cancer_prevention.Main.Main_bar
import com.example.cancer_prevention.Question_Community.Question_Community
import com.example.cancer_prevention.Retrofit.Cancer_Retrofit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_bar.*
import kotlinx.android.synthetic.main.activity_main_bar.view.*

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    val fragment = Main_Fragment()

    val main_bar = Main_bar()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*

        gogo_corutine.setOnClickListener {
            startActivity(Intent(this@MainActivity, corutine::class.java))
        }


         */

        var main = findViewById<ChipNavigationBar>(R.id.bottonavi)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        bottonavi.setItemSelected(R.id.first)
        bottonavi.setOnItemSelectedListener {
            when (it) {
                R.id.first -> {
                    val MainFragment1 = Main_Fragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment1).commit()
                }
                R.id.second -> {
                    val MainFragment2 = Community_Fragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment2).commit()
                }
                R.id.third -> {
                    val MainFragment3 = Introduce_Screen()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment3).commit()
                }
            }
        }



        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_format_list_bulleted_24) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        main_drawer_layout.bar_title.setOnClickListener {
            Toast.makeText(this@MainActivity, "하하",Toast.LENGTH_SHORT).show()
        }

        main_navigationView.setNavigationItemSelectedListener(this) //navigation 리스너

        /*

        main.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.first -> {
                        val MainFragment1 = Main_Fragment()
                        var haha = BuildConfig.GOOGLE_API_KEY
                        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment1).commit()
                    }
                    R.id.second -> {
                        val MainFragment2 = Community_Fragment()
                        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment2).commit()
                    }
                    R.id.third -> {
                        val MainFragment3 = Introduce_Screen()
                        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment3).commit()
                    }

                }
                true
            }
            selectedItemId = R.id.first
        }

         */

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                main_drawer_layout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item1-> onFragmentChanged(1)
            R.id.menu_item2-> onFragmentChanged(3)
            R.id.menu_item3-> Toast.makeText(this,"item3 clicked",Toast.LENGTH_SHORT).show()
        }
        return false
    }

    fun onFragmentChanged(index: Int) {

        if (index == 0) {
            val MainFragment1 = Main_Fragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment1).commitAllowingStateLoss()
            bottonavi.setItemSelected(R.id.first)
        } else if (index == 1) {
            bottonavi.setItemSelected(R.id.second)
            val MainFragment2 = Community_Fragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment2).commitAllowingStateLoss()
            main_drawer_layout.closeDrawer(GravityCompat.START)
        } else if (index == 2) {
            val MainFragment3 = Introduce_Screen()
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment3).commitAllowingStateLoss()
            bottonavi.setItemSelected(R.id.first)
        } else if (index == 3) {
            bottonavi.setItemSelected(R.id.second)
            val Question_Community = Question_Community()
            supportFragmentManager.beginTransaction().replace(R.id.container, Question_Community).commit()
            main_drawer_layout.closeDrawer(GravityCompat.START)
        }
    }
}