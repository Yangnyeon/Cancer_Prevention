package com.example.cancer_prevention.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.cancer_prevention.MainActivity
import com.example.cancer_prevention.R

class Intro_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)

        var handler = Handler()
        handler.postDelayed( {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 1500)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}