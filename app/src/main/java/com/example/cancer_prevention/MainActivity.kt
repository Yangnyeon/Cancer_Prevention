package com.example.cancer_prevention

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cancer_prevention.Retrofit.Cancer_Retrofit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gogo_retrofit.setOnClickListener {
            startActivity(Intent(this@MainActivity, Cancer_Retrofit::class.java))
        }

    }
}