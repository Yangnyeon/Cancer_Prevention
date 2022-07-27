package com.example.cancer_prevention

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cancer_prevention.Community.Community_Fragment
import com.example.cancer_prevention.Main.Main_Fragment
import com.example.cancer_prevention.Retrofit.Cancer_Retrofit
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*

        gogo_corutine.setOnClickListener {
            startActivity(Intent(this@MainActivity, corutine::class.java))
        }


         */

        var main = findViewById<BottomNavigationView>(R.id.bottonavi)



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
                }
                true
            }
            selectedItemId = R.id.first
        }

    }
}