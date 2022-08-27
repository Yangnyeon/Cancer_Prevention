package com.example.cancer_prevention.Main.Notice

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.Main.Notice.Notice_Adapter
import com.example.cancer_prevention.Main.Notice.Notice_Layout
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityCommunityHolderBinding
import com.example.cancer_prevention.databinding.ActivityNoticeBinding
import com.example.cancer_prevention.databinding.FragmentIntroduceScreenBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class Notice_Activity : AppCompatActivity() {


    private lateinit var binding: ActivityNoticeBinding

    val db = FirebaseFirestore.getInstance()

    val itemList = arrayListOf<Notice_Layout>()    // 리스트 아이템 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.activity_cloud_firestore)
        setContentView(view)

        CoroutineScope(Dispatchers.Main).launch {

            //혹시모를 ANR 방지로 코루틴으로 비동기처리

            val notice_adapter = Notice_Adapter(itemList, this@Notice_Activity)

            val loadingAnimDialog = loading_screen(this@Notice_Activity)

            loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            loadingAnimDialog.show()


            binding.noticeRecyclerview.layoutManager = LinearLayoutManager(this@Notice_Activity, LinearLayoutManager.VERTICAL, false)
            binding.noticeRecyclerview.adapter = notice_adapter

                db.collection("Notice") // 작업할 컬렉션
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



        }

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }





    }
}