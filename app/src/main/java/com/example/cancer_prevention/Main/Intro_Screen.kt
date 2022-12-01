package com.example.cancer_prevention.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.cancer_prevention.MainActivity
import com.example.cancer_prevention.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Intro_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)

        /*
        var handler = Handler()
        handler.postDelayed( {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 1500)
        
         */

            var intent = Intent(this, MainActivity::class.java)
            CoroutineScope(Dispatchers.Main).launch {
                delay(1500)
                startActivity(intent)
                //코루틴을 이용한 인트로화면 설계
            }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}