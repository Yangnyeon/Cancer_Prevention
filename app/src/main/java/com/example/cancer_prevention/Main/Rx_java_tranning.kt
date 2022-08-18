package com.example.cancer_prevention.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.cancer_prevention.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.operators.observable.ObservableAll
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import kotlinx.android.synthetic.main.activity_rx_java_tranning.*


import android.widget.TextView

import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.rxjava3.core.Flowable.interval
import io.reactivex.rxjava3.core.Observable.interval
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromIterable
import kotlinx.coroutines.*
import java.lang.Runnable
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.concurrent.timer


class Rx_java_tranning : AppCompatActivity() {

    var time = 0

    var timerTask : Timer? = null

    lateinit var th : Thread
    lateinit var th2 : Thread
    var isThread = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_tranning)


        /*
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000) // 코루틴의 진행을 1초 대기시킵니다.
            // Thread.sleep(1000) // 스레드의 진행을 1초 대기시킵니다.
            rx_java.text = "민어어엄진화아아아"
        }

         */


        /*
        btn_start.setOnClickListener {

            isThread = true
            th = Thread() {
                run() {
                    while (isThread) {
                        sleep(1000L)
                        handler.sendEmptyMessage(0)
                    }
                }
            }

            th.start()

        }

        btn_stop.setOnClickListener {
            isThread = false
        }

         */



    }

    val handler = Handler() {
        Toast.makeText(this@Rx_java_tranning, "하하",Toast.LENGTH_SHORT).show()
        true
    }

    fun startTimer() {
        timerTask = timer(period = 10) {
            time++

            val sec = time / 100
            val milli = time % 10

            runOnUiThread{
                rx_java.text = "${sec} : ${milli}"
            }

        }
    }

    fun stopTimer() {
        timerTask?.cancel()
    }

    fun main() {
        runBlocking<Unit> { // start main coroutine
          var job = GlobalScope.launch { // launch new coroutine in background and continue
                delay(1000L)
                rx_java.append("World!")
            }
            rx_java.append("Hello") // main coroutine continues here immediately
           job.join()
        }
        rx_java.append("하하")

    }

    fun main2() {
        runBlocking {
            launch {
                delay(100L)
                rx_java.append("1등" + "\n")
            }

            coroutineScope {
                launch {
                    delay(200L)
                    rx_java.append("2등" + "\n")
                }

                delay(300L)
                rx_java.append("3등" + "\n")
            }
        }

        rx_java.append("끗")


    }

    fun main3() {
        runBlocking {
            launch {
                delay(100L)
                rx_java.append("1등")
            }
            launch {
                delay(500L)
                rx_java.append("2등")
            }
            launch {
                delay(900L)
                rx_java.append("3등")
            }
        }

    }




}