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
import java.util.concurrent.Callable


class Rx_java_tranning : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_tranning)

        /*
        val items = ReplaySubject.create<Int>()

        for(i in 0..6) {
            items.onNext(i)
        }

        items.filter { item: Int -> item % 2 == 0 }
            .subscribe { x: Int? -> println(x) }

        items.filter { item : Int -> item % 2 == 0 }
            .subscribe{ x : Int? -> rx_java.append(x.toString())
        }

         */

        /*

        var haha = ArrayList<String>()
        haha.add("어어")
        haha.add("끅끅끅")


        val source4: Observable<String> = Observable.fromIterable(haha)

        source4.subscribe{x : String? -> rx_java.append(x)}

         */





        //0809

        /*
        val source: Observable<String> = Observable.create { emitter ->
            emitter.onNext("Hello")
            emitter.onNext("Yena")
            emitter.onComplete();
        }

        source.subscribe{ x : String? -> rx_java.append(x + "\n")}


         */

        /*
        var names = ArrayList<String>()

        names.add("하하")
        names.add("민어어엄")

        val sources : Observable<String> = ObservableFromIterable(names)

        sources.subscribe{x : String -> rx_java.append(x + " ")}

         */

        /*

        var names = ArrayList<String>()

        names.add("하하")
        names.add("민어어엄")

        val callable: Callable<String> = Callable<String> {
            Thread.sleep(3000)
            "Hello Callable"
        }

        val sources : Observable<String> = Observable.fromCallable(callable)
        sources.subscribe{x : String? -> rx_java.append(x + " ")}

        val sources2 : Observable<String> = ObservableFromIterable(names)
        sources2.subscribe{x : String? -> rx_java.append(x + " ")}

         */




    }
}