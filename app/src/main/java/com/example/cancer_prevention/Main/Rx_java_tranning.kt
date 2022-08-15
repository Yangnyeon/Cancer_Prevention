package com.example.cancer_prevention.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cancer_prevention.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.operators.observable.ObservableAll
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import kotlinx.android.synthetic.main.activity_rx_java_tranning.*


import android.widget.TextView

import android.util.Log
import android.view.View
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromIterable
import kotlinx.coroutines.*
import java.util.concurrent.Callable


class Rx_java_tranning : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_tranning)



        //main()

       // main2()

        main3()




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