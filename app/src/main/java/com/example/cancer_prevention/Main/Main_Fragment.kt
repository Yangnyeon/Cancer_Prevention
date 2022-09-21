package com.example.cancer_prevention.Main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.cancer_prevention.Nutrient.nutrient_screen
import com.example.cancer_prevention.R
import com.example.cancer_prevention.Retrofit.Cancer_Retrofit
import com.example.cancer_prevention.databinding.FragmentMainBinding
import com.example.cancer_prevention.room.Calendar.Calendar_Room
import com.example.cancer_prevention.room.Cancer_Room
import com.example.cancer_prevention.room.Room_Activity
import com.example.cancer_prevention.room.liver_Screen
import com.google.common.reflect.Reflection.getPackageName
import kotlinx.android.synthetic.main.cancer_nutrient.*
import kotlinx.android.synthetic.main.fragment_main_.*
import java.lang.Package.getPackage
import java.lang.Package.getPackages
import android.view.WindowManager

import android.view.Display

import android.util.AttributeSet

import android.widget.VideoView
import android.util.DisplayMetrics
import android.media.MediaPlayer

import android.media.MediaPlayer.OnVideoSizeChangedListener
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.Bad_Food.Bad_Food_Activity
import com.example.cancer_prevention.BuildConfig.*
import com.example.cancer_prevention.Community.ListAdapter
import com.example.cancer_prevention.Community.ListLayout
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.Main.Notice.Notice_Adapter
import com.example.cancer_prevention.Main.Notice.Notice_Layout
import com.example.cancer_prevention.MainActivity
import com.example.cancer_prevention.Nutrient.nutrient_model
import com.google.android.gms.common.api.GoogleApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_rx_java_tranning.*
import kotlinx.coroutines.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.lang.Runnable
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory


var korona : String = ""

var real_korona = ""

var arr = ""

class Main_Fragment : Fragment() {

    private var _binding : FragmentMainBinding ?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    var currentPosition = 0

    //공지사항

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<Notice_Layout>()    // 리스트 아이템 배열

    //


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentMainBinding.inflate(inflater, container, false)


        binding.gogoRetrofit.setOnClickListener {
            startActivity(Intent(requireActivity(), Cancer_Retrofit::class.java))
        }

        binding.gogoRoom.setOnClickListener {
            startActivity(Intent(requireActivity(), Room_Activity::class.java))
        }

        binding.SelectCigaretteCalendar.setOnClickListener {
            startActivity(Intent(requireActivity(), Calendar_Room::class.java))
        }


        binding.NutrientGogo.setOnClickListener {
            startActivity(Intent(requireActivity(), nutrient_screen::class.java))
        }



        binding.gogoRetrofit2.setOnClickListener {
            startActivity(Intent(requireActivity(), Cancer_Room::class.java))
        }

        binding.SelectCigaretteGan.setOnClickListener {
            startActivity(Intent(requireActivity(), liver_Screen::class.java))
        }

        binding.SelectGogo2.setOnClickListener {
            startActivity(Intent(requireActivity(), Rx_java_tranning::class.java))
        }

        binding.SelectGogo3.setOnClickListener {
            startActivity(Intent(requireActivity(), Bad_Food_Activity::class.java))
        }

        val adapter11 = ViewPagerAdapter()
        binding.viewpager11.adapter = adapter11

        /*
        val thread = Thread(PagerRunnable())
        thread.start()

         */

        var bounce = AnimationUtils.loadAnimation(requireActivity(),R.anim.bounce)

        var turn_aruond = AnimationUtils.loadAnimation(requireActivity(), R.anim.turn_around)

        val interpolator = BounceInterpolator()

        bounce.interpolator = interpolator
        bounce.repeatCount = Animation.INFINITE
        bounce.repeatMode = Animation.REVERSE
        bounce.fillAfter = true


        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            binding.cancerImage1.startAnimation(bounce)
            delay(200)
            binding.cancerImage2.startAnimation(turn_aruond)
         }
        binding.cancerImage3.startAnimation(bounce)

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(2000)
                setPage()
            }
        } //뷰페이저

        //공지사항


            val notice_adapter = Notice_Adapter(itemList, requireActivity())

            val loadingAnimDialog = loading_screen(requireActivity())

            loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            loadingAnimDialog.show()


            binding.noticeList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            binding.noticeList.adapter = notice_adapter

            db.collection("Notice") // 작업할 컬렉션
                .limit(3)
                .orderBy("Notice_date", Query.Direction.DESCENDING)
                .get() // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    loadingAnimDialog.dismiss()

                    itemList.clear()

                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item =
                            Notice_Layout(
                                document["Notice_name"] as String,
                                document["Notice_content"] as String,
                                document["Notice_date"] as String?,
                                document["Notice_doc"] as String,
                                document["Notice_image"] as String
                            )
                        itemList.add(item)
                    }
                    notice_adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우z
                    Log.w("MainActivity", "Error getting documents: $exception")
                }


        korona()

        if (binding.koronaText.text == "") {
            val yesterday_now = System.currentTimeMillis() - 1000*60*60*24
            // val simpleDate = SimpleDateFormat("yyyyMMdd")
            val simpleDate = SimpleDateFormat("yyyy")

            val date_year = Date(yesterday_now)

            val getYear = simpleDate.format(date_year)
            //

            val simpleDate_month = SimpleDateFormat("MM")

            val date_month = Date(yesterday_now)

            val getmonth = simpleDate_month.format(date_month)

            //

            val simpleDate_day = SimpleDateFormat("dd")

            val date_day = Date(yesterday_now)

            val getday = simpleDate_day.format(date_day)

            val key = GOOGLE_API_SERVICE3

            val pageNo = "&pageNo=1"

            val numOfRows ="&numOfRows=10"

            val startCreateDt = "&startCreateDt=" + getYear + getmonth + getday


            val endCreateDt = "&endCreateDt=" + getYear + getmonth + getday

            val url = "Korona_address+key+pageNo+numOfRows+startCreateDt+endCreateDt"

            val thread = Thread(NetworkThread(url))
            thread.start()
            thread.join()

            binding.koronaText.text = korona
        }




        return binding.root
    }

    //


    //코로나 확진자수
    class NetworkThread(var url: String): Runnable {

        override fun run() {

            try {

                val xml: Document =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)


                xml.documentElement.normalize()

           
                val list: NodeList = xml.getElementsByTagName("item")

   
                for (i in 0..list.length - 1) {

                    if(i == list.length -1) {

                        val n: Node = list.item(i)

                        if (n.getNodeType() == Node.ELEMENT_NODE) {

                            val elem = n as Element

                            val map = mutableMapOf<String, String>()


                            for (j in 0..elem.attributes.length - 1) {

                                map.putIfAbsent(
                                    elem.attributes.item(j).nodeName,
                                    elem.attributes.item(j).nodeValue
                                )

                            }

                            korona = "금일 코로나 확진자수 : ${elem.getElementsByTagName("incDec").item(0).textContent}" + " 명"

                        }
                    }

                }
            } catch (e: Exception) {

            }
        }

    }
    //


    private fun setPage() {
        if(currentPosition == 3 ) {
            currentPosition = 0
        }
        if(viewpager11 != null) {
            viewpager11.setCurrentItem(currentPosition, true)
        }

        currentPosition += 1
    }

    fun korona() {
        val now = System.currentTimeMillis()
        // val simpleDate = SimpleDateFormat("yyyyMMdd")
        val simpleDate = SimpleDateFormat("yyyy")

        val date_year = Date(now)

        val getYear = simpleDate.format(date_year)
        //

        val simpleDate_month = SimpleDateFormat("MM")

        val date_month = Date(now)

        val getmonth = simpleDate_month.format(date_month)

        //

        val simpleDate_day = SimpleDateFormat("dd")

        val date_day = Date(now)

        val getday = simpleDate_day.format(date_day)

        val key = GOOGLE_API_SERVICE3

        val pageNo = "&pageNo=1"

        val numOfRows ="&numOfRows=10"

        val startCreateDt = "&startCreateDt=" + getYear + getmonth + getday

        val endCreateDt = "&endCreateDt=" + getYear + getmonth + getday

        val url = Korona_address+key+pageNo+numOfRows+startCreateDt+endCreateDt

        val thread = Thread(NetworkThread(url))
        thread.start()
        thread.join()

        binding.koronaText.text = korona
    }


    inner class PagerRunnable : Runnable {
        override fun run() {
            while(true) {
                Thread.sleep(1500)
                handler.sendEmptyMessage(0)
            }
        }
    }


}