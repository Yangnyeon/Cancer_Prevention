package com.example.cancer_prevention.Main.Notice

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Community.Comment.Comment_ListAdapter
import com.example.cancer_prevention.Community.Comment.Comment_ListLayout
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityCommunityHolderBinding
import com.example.cancer_prevention.databinding.ActivityNoticeHolderBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_community_holder.*
import kotlinx.android.synthetic.main.activity_community_holder.board_content
import kotlinx.android.synthetic.main.activity_community_holder.board_date
import kotlinx.android.synthetic.main.activity_community_holder.board_title
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import kotlinx.android.synthetic.main.activity_notice_holder.*

class Notice_holder : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeHolderBinding

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeHolderBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.activity_cloud_firestore)
        setContentView(view)

        val loadingAnimDialog = loading_screen(this@Notice_holder)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        intent = intent // 인텐트 받아오기

        val Notice_doc = intent.getStringExtra("Notice_doc")


        db.collection("Notice")
            .document(Notice_doc.toString())
            .get()
            .addOnSuccessListener { result ->

                loadingAnimDialog.dismiss()

                try {
                    with(result) {
                        Notice_holder_name.text = "${getString("Notice_name")}"
                        Notice_holder_content.text = "${getString("Notice_content")}"
                        Notice_holder_date.text = "${getString("Notice_date")}"
                        Glide.with(this@Notice_holder)
                            .load("${getString("Notice_image")}")
                            .fallback(null)
                            .into(Notice_holder_image)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@Notice_holder, e.toString() , Toast.LENGTH_SHORT).show()
                }
            }

        binding.NoticeHolderBack.setOnClickListener {
            finish()
        }

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }

    }
}