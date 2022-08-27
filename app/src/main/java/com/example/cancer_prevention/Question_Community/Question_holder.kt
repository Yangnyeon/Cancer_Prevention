package com.example.cancer_prevention.Question_Community

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Community.Comment.Comment_ListAdapter
import com.example.cancer_prevention.Community.Comment.Comment_ListLayout
import com.example.cancer_prevention.Community.Community_holder
import com.example.cancer_prevention.Community.ListLayout
import com.example.cancer_prevention.Question_CommunZity.Question_Comment.Question_Comment_ListAdapter
import com.example.cancer_prevention.Question_Community.Question_Comment.Question_Comment_ListLayout
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityCommunityHolderBinding
import com.example.cancer_prevention.databinding.ActivityQuestionHolderBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_community_holder.*
import kotlinx.android.synthetic.main.activity_community_holder.comment_edit
import kotlinx.android.synthetic.main.activity_community_holder.commnet_button
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import kotlinx.android.synthetic.main.activity_question_holder.*
import java.text.SimpleDateFormat
import java.util.*

class Question_holder : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionHolderBinding

    val db = FirebaseFirestore.getInstance()

    val itemList2 = arrayListOf<Question_Comment_ListLayout>()

    var adapter = Question_Comment_ListAdapter(itemList2, this)

    val itemList = arrayListOf<Question_Layout>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_holder)
        binding = ActivityQuestionHolderBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.activity_cloud_firestore)
        setContentView(view)


        binding.QuestionRecyclerViewCommunityComment.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.QuestionRecyclerViewCommunityComment.adapter = adapter

        //

        intent = intent // 인텐트 받아오기


        val holder_doc = intent.getStringExtra("Question_board_doc")
        val password = intent.getStringExtra("board_password")

        // board_title.setText(title)
        // board_date.setText(content)
        // board_content.setText(date)
        // board_nickname.text = nickname.toString()
        //likes.text = like_count.toString()

        var settings: SharedPreferences = getSharedPreferences("Question_like_tmp", MODE_PRIVATE)

        var editor: SharedPreferences.Editor = settings.edit()

        if(settings.getBoolean(holder_doc.toString(), false))
        {
            Question_notliked.visibility = View.INVISIBLE
            Question_liked.visibility = View.VISIBLE
        }
        else
        {
            Question_notliked.visibility = View.VISIBLE
            Question_liked.visibility = View.INVISIBLE
        }

        db.collection("Question")
            .document(holder_doc.toString())
            .get()
            .addOnSuccessListener { result ->
                try {
                    with(result) {
                        Question_board_title.text = "${getString("Question_name")}"
                        Question_board_date.text = "${getString("Question_date")}"
                        Question_board_content.text = "${getString("Question_content")}"
                        Question_board_nickname.text = "${getString("Question_nickname")}"
                        Question_likes.text = "${getLong("Question_liked")}"
                        Question_eye_holder_count.text = "조회수 : ${getLong( "Question_eye")}"
                        Glide.with(this@Question_holder)
                            .load("${getString("Question_imageUrl")}")
                            .fallback(null)
                            .into(Question_real_holder_image)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@Question_holder, e.toString() , Toast.LENGTH_SHORT).show()
                }
            }



        val Question_go_comment_delelte = Intent(this@Question_holder, Community_holder::class.java)
        Question_go_comment_delelte.putExtra("Question_board_doc", holder_doc)





        //댓글출력
        db.collection("Question")
            .document(holder_doc.toString())
            .collection("Question_Comment")// 작업할 컬렉션
            .orderBy("Question_Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList2.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item2 =
                        Question_Comment_ListLayout(
                            document["Question_Comment"] as String,
                            document["Question_Date"] as String,
                            document["Question_Doc"] as String,
                            document["Question_comment_password"] as String,
                            document["Question_content_doc"] as String,
                            document["Question_Content_nickname"] as String,
                            document["Question_Comment_liked"] as Long
                        )
                    itemList2.add(item2)
                }
                adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("TAG", "Error getting documents: $exception")
            }

        //



        //댓글입력

        Question_commnet_button.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.comment_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            //mBuilder.show()

            val mAlertDialog = mBuilder.show()

            val comment_upload = mDialogView.findViewById<CardView>(R.id.comment_upload)
            val comment_cancle = mDialogView.findViewById<CardView>(R.id.comment_cancle)
            val edit_nickname = mDialogView.findViewById<EditText>(R.id.edit_nickname)
            val edit_password = mDialogView.findViewById<EditText>(R.id.edit_password)

            comment_upload.setOnClickListener {

                var comment_edit = Question_comment_edit.text.toString()
                val currentTime: Long = System.currentTimeMillis()
                val simpleDate = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
                val mDate: Date = Date(currentTime)
                val getTime = simpleDate.format(mDate)
                val content_doc = holder_doc.toString()
                val content_nickname =  edit_nickname.text.toString()

                val doc = UUID.randomUUID().toString()

                val data = hashMapOf(
                    "Question_Comment" to comment_edit,
                    "Question_Date" to getTime.toString(),
                    "Question_Doc" to doc,
                    "Question_comment_password" to edit_password.text.toString(),
                    "Question_content_doc" to content_doc,
                    "Question_Content_nickname" to content_nickname,
                    "Question_Comment_liked" to 0.toLong()
                )

                db.collection("Question")
                    .document(holder_doc.toString())
                    .collection("Question_Comment")
                    .document(doc.toString())
                    .set(data)
                    .addOnSuccessListener {
                        // 성공할 경우
                        Toast.makeText(this, "데이터가 추가되었습니다", Toast.LENGTH_SHORT).show()

                        update()

                        mAlertDialog.dismiss()


                        //go_board2.putExtra("board_doc", it.toString())
                        // startActivity(go_board2)
                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우

                        Log.w("MainActivity", "Error getting documents: $exception")
                    }
            }

            comment_cancle.setOnClickListener {
                mAlertDialog.dismiss()
            }



        }



        Question_liked.setOnClickListener {
            Question_notliked.visibility = View.VISIBLE
            Question_liked.visibility = View.INVISIBLE

            db.collection("Question")
                .document(holder_doc.toString())
                .update("Question_liked", FieldValue.increment(-1))
                .addOnSuccessListener { result ->

                    db.collection("Question")
                        .document(holder_doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {

                                    Question_likes.text = "${getLong("Question_liked")}"
                                    editor.remove(holder_doc.toString())
                                    editor.commit()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(this@Question_holder, e.toString() , Toast.LENGTH_SHORT).show()
                            }
                        }


                    Toast.makeText(this@Question_holder, "좋아요를 취소했습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this@Question_holder, exception.toString(), Toast.LENGTH_SHORT).show()
                }

        }

        Question_notliked.setOnClickListener {
            Question_notliked.visibility = View.INVISIBLE
            Question_liked.visibility = View.VISIBLE
            //여기서 부터 시작
            db.collection("Question")
                .document(holder_doc.toString())
                .update("Question_liked", FieldValue.increment(1))
                .addOnSuccessListener { result ->

                    //

                    db.collection("Question")
                        .document(holder_doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {

                                    Question_likes.text = "${getLong("Question_liked")}"
                                    editor.putBoolean(holder_doc.toString(), true)
                                    editor.commit()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(this@Question_holder, e.toString() , Toast.LENGTH_SHORT).show()
                            }
                        }


                    //


                    Toast.makeText(this@Question_holder, "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this@Question_holder, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        }

        /*좋아요 출력

        db.collection("Contacts")
            .document(holder_doc.toString())
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                if (result != null) {
                    likes.text = result.data.toString()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        */

        Question_content_delete.setOnClickListener {

            val builder = AlertDialog.Builder(this@Question_holder)


            // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
            val tvName = TextView(this@Question_holder)
            tvName.text = "\n비밀번호 입력\n"

            val password_edit = EditText(this@Question_holder)
            password_edit.isSingleLine = true

            val mLayout = LinearLayout(this@Question_holder)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)

            mLayout.addView(tvName)
            mLayout.addView(password_edit)

            builder.setView(mLayout)

            builder.setTitle("게시물 삭제")
            builder.setPositiveButton("삭제") { dialog, which ->
                // EditText에서 문자열을 가져와 hashMap으로 만듦

                if(password_edit.text.toString().equals(password.toString())) {
                    db.collection("Question")
                        .document(holder_doc.toString())
                        .delete()
                        .addOnSuccessListener {
                            // 성공할 경우
                            Toast.makeText(this, "성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()

                            finish()
                            //go_board2.putExtra("board_doc", it.toString())
                            // startActivity(go_board2)
                        }
                        .addOnFailureListener { exception ->
                            // 실패할 경우

                            Log.w("MainActivity", "Error getting documents: $exception")
                        }
                } else {
                    Toast.makeText(this, "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show()
                }



            }
            builder.setNegativeButton("취소") { dialog, which ->

            }
            builder.show()



        }

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }




    }

    fun update() {

        val holder_doc = intent.getStringExtra("Question_board_doc")

        db.collection("Question")
            .document(holder_doc.toString())
            .collection("Question_Comment")// 작업할 컬렉션
            .orderBy(" Question_Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList2.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item2 =
                        Question_Comment_ListLayout(
                            document["Question_Comment"] as String,
                            document["Question_Date"] as String,
                            document["Question_Doc"] as String,
                            document["Question_comment_password"] as String,
                            document["Question_content_doc"] as String,
                            document["Question_Content_nickname"] as String,
                            document["Question_Comment_liked"] as Long
                        )
                    itemList2.add(item2)
                }
                adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("TAG", "Error getting documents: $exception")
            }
    }

}