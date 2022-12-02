package com.example.cancer_prevention.Community

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
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Community.Comment.Comment_ListAdapter
import com.example.cancer_prevention.Community.Comment.Comment_ListLayout
import com.example.cancer_prevention.MainActivity
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityCommunityHolderBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_community_holder.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*
import java.text.SimpleDateFormat
import java.util.*

class Community_holder : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityHolderBinding

    val db = FirebaseFirestore.getInstance()

    val itemList2 = arrayListOf<Comment_ListLayout>()

    var adapter = Comment_ListAdapter(itemList2, this)

    val itemList = arrayListOf<ListLayout>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_holder)


        binding = ActivityCommunityHolderBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.activity_cloud_firestore)
        setContentView(view)


        binding.recyclerViewCommunityComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCommunityComment.adapter = adapter




        //


        intent = intent // 인텐트 받아오기

        val title = intent.getStringExtra("board_title") //Adapter에서 받은 키값 연결
        val date = intent.getStringExtra("board_date")
        val content = intent.getStringExtra("board_content")
        val holder_doc = intent.getStringExtra("board_doc")
        val password = intent.getStringExtra("board_password")
        val like_count = intent.getStringExtra("board_liked")
        val nickname = intent.getStringExtra("board_Nickname")

        // board_title.setText(title)
        // board_date.setText(content)
        // board_content.setText(date)
        // board_nickname.text = nickname.toString()
        //likes.text = like_count.toString()

        var settings: SharedPreferences = getSharedPreferences("like_tmp", MODE_PRIVATE)

        var editor: SharedPreferences.Editor = settings.edit()

        if(settings.getBoolean(holder_doc.toString(), false))
        {
            notliked.visibility = View.INVISIBLE
            liked.visibility = View.VISIBLE
        }
        else
        {
            notliked.visibility = View.VISIBLE
            liked.visibility = View.INVISIBLE
        }

        db.collection("Contacts")
            .document(holder_doc.toString())
            .get()
            .addOnSuccessListener { result ->
                try {
                    with(result) {

                        board_title.text = "${getString("name")}"
                        board_date.text = "${getString("com_date")}"
                        board_content.text = "${getString("number")}"
                        board_nickname.text = "${getString("nickname")}"
                        likes.text = "${getLong("liked")}"
                        eye_holder_count.text = "조회수 : ${getLong("eye_count")}"

                        /*
                        Glide.with(this@Community_holder)
                            .load("${getString("imageUrl")}")
                            .fallback(null)
                            .into(real_holder_image)

                         */

                        binding.realHolderImage
                            .load("${getString("imageUrl")}"){
                            placeholder(null)
                            error(null)
                        }


                    }
                } catch (e: Exception) {
                    Toast.makeText(this@Community_holder, e.toString() , Toast.LENGTH_SHORT).show()
                }
            }



        val go_comment_delelte = Intent(this@Community_holder, Community_holder::class.java)
        go_comment_delelte.putExtra("board_doc", holder_doc)





        //댓글출력
        db.collection("Contacts")
            .document(holder_doc.toString())
            .collection("Comment")// 작업할 컬렉션
            .orderBy("Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList2.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item2 =
                        Comment_ListLayout(
                            document["Comment"] as String,
                            document["Date"] as String,
                            document["Doc"] as String,
                            document["comment_password"] as String,
                            document["content_doc"] as String,
                            document["Content_nickname"] as String,
                            document["Comment_liked"] as Long
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

        commnet_button.setOnClickListener {

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

                var comment_edit = comment_edit.text.toString()
                val currentTime: Long = System.currentTimeMillis()
                val simpleDate = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
                val mDate: Date = Date(currentTime)
                val getTime = simpleDate.format(mDate)
                val content_doc = holder_doc.toString()
                val content_nickname =  edit_nickname.text.toString()

                val doc = UUID.randomUUID().toString()

                val data = hashMapOf(
                    "Comment" to comment_edit,
                    "Date" to getTime.toString(),
                    "Doc" to doc,
                    "comment_password" to edit_password.text.toString(),
                    "content_doc" to content_doc,
                    "Content_nickname" to content_nickname,
                    "Comment_liked" to 0.toLong()
                )

                db.collection("Contacts")
                    .document(holder_doc.toString())
                    .collection("Comment")
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



        liked.setOnClickListener {
            notliked.visibility = View.VISIBLE
            liked.visibility = View.INVISIBLE

            db.collection("Contacts")
                .document(holder_doc.toString())
                .update("liked", FieldValue.increment(-1))
                .addOnSuccessListener { result ->

                    db.collection("Contacts")
                        .document(holder_doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {

                                    likes.text = "${getLong("liked")}"
                                    editor.remove(holder_doc.toString())
                                    editor.commit()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(this@Community_holder, e.toString() , Toast.LENGTH_SHORT).show()
                            }
                        }


                    Toast.makeText(this@Community_holder, "좋아요를 취소했습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this@Community_holder, exception.toString(), Toast.LENGTH_SHORT).show()
                }

        }

        notliked.setOnClickListener {
            notliked.visibility = View.INVISIBLE
            liked.visibility = View.VISIBLE
            //여기서 부터 시작
            db.collection("Contacts")
                .document(holder_doc.toString())
                .update("liked", FieldValue.increment(1))
                .addOnSuccessListener { result ->

                    //


                    db.collection("Contacts")
                        .document(holder_doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {

                                    likes.text = "${getLong("liked")}"
                                    editor.putBoolean(holder_doc.toString(), true)
                                    editor.commit()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(this@Community_holder, e.toString() , Toast.LENGTH_SHORT).show()
                            }
                        }


                    //


                    Toast.makeText(this@Community_holder, "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this@Community_holder, exception.toString(), Toast.LENGTH_SHORT).show()
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

        content_delete.setOnClickListener {

            val builder = AlertDialog.Builder(this@Community_holder)


            // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
            val tvName = TextView(this@Community_holder)
            tvName.text = "\n비밀번호 입력\n"

            val password_edit = EditText(this@Community_holder)
            password_edit.isSingleLine = true

            val mLayout = LinearLayout(this@Community_holder)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)

            mLayout.addView(tvName)
            mLayout.addView(password_edit)

            builder.setView(mLayout)

            builder.setTitle("게시물 삭제")
            builder.setPositiveButton("삭제") { dialog, which ->
                // EditText에서 문자열을 가져와 hashMap으로 만듦

                if(password_edit.text.toString().equals(password.toString())) {
                    db.collection("Contacts")
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

        val content_doc = intent.getStringExtra("board_doc")

        db.collection("Contacts")
            .document(content_doc.toString())
            .collection("Comment")// 작업할 컬렉션
            .orderBy("Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList2.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item2 =
                        Comment_ListLayout(
                            document["Comment"] as String,
                            document["Date"] as String,
                            document["Doc"] as String,
                            document["comment_password"] as String,
                            document["content_doc"] as String,
                            document["Content_nickname"] as String,
                            document["Comment_liked"] as Long)
                    itemList2.add(item2)
                }
                adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
                comment_edit.setText("")
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("TAG", "Error getting documents: $exception")
            }
    }




}