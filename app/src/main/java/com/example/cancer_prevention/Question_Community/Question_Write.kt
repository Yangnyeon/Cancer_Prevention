package com.example.cancer_prevention.Question_Community

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.cancer_prevention.Community.ListLayout
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_community_write.*
import kotlinx.android.synthetic.main.activity_question_write.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Question_Write : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    val itemList2 = arrayListOf<ListLayout>()

    //이미지 업로드

    lateinit var storage: FirebaseStorage

    private val IMAGE_PICK=1111

    var selectImage: Uri?=null


    //

    val Question_list = arrayListOf<Question_Layout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_write)

        storage = FirebaseStorage.getInstance()



        Question_upload.setOnClickListener(View.OnClickListener {

            Question_saveNote()

        })


        Question_image_upload.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK) //선택하면 무언가를 띄움. 묵시적 호출
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectImage = data?.data
            Question_image_upload.setImageURI(selectImage)
        }
    }

    private fun Question_saveNote() {

        CoroutineScope(Dispatchers.Main).launch {

            val title: String = Question_name.text.toString()
            val description: String = Question_content.text.toString()
            val nickname: String = Question_nickname.text.toString()
            val now = System.currentTimeMillis()
            val simpleDate = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
            val mDate = Date(now)
            val getTime = simpleDate.format(mDate)
            val password = Question_password.text.toString()

            val doc = UUID.randomUUID().toString()

            val loadingAnimDialog = loading_screen(this@Question_Write)

            loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            loadingAnimDialog.show()

            if (selectImage != null) {
                if (title.trim { it <= ' ' }.isEmpty() || description.isEmpty()) {
                    Toast.makeText(this@Question_Write, "입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    var fileName =
                        SimpleDateFormat("yyyyMMddHHmmss").format(Date()) // 파일명이 겹치면 안되기 떄문에 시년월일분초 지정
                    storage.reference.child("Question_image").child(fileName)
                        .putFile(selectImage!!)//어디에 업로드할지 지정
                        .addOnSuccessListener { taskSnapshot -> // 업로드 정보를 담는다
                            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { it ->
                                //var imageUrl=it.toString()
                                //var photo= Photo(textEt.text.toString(),imageUrl)

                                val data = hashMapOf(
                                    "Question_name" to title.toString(),
                                    "Question_content" to description.toString(),
                                    "Question_date" to getTime.toString(),
                                    "Question_password" to password.toString(),
                                    "Question_doc" to doc,
                                    "Question_nickname" to nickname.toString(),
                                    "Question_liked" to 0.toLong(),
                                    "Question_eye" to 0.toLong(),
                                    "Question_imageUrl" to it.toString()
                                )

                                db.collection("Question")
                                    .document(doc)
                                    .set(data)
                                    .addOnSuccessListener {

                                        loadingAnimDialog.dismiss()

                                        Toast.makeText(
                                            this@Question_Write,
                                            "게시물이 업로드 되었습니다!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        finish()
                                    }
                                    .addOnFailureListener { exception ->
                                        // 실패할 경우
                                        Toast.makeText(
                                            this@Question_Write,
                                            "실패하였습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                }
            } else {
                if (title.trim { it <= ' ' }.isEmpty() || description.isEmpty()) {
                    Toast.makeText(this@Question_Write, "입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    val data = hashMapOf(
                        "Question_name" to title.toString(),
                        "Question_content" to description.toString(),
                        "Question_date" to getTime.toString(),
                        "Question_password" to password.toString(),
                        "Question_doc" to doc,
                        "Question_nickname" to nickname.toString(),
                        "Question_liked" to 0.toLong(),
                        "Question_eye" to 0.toLong(),
                        "Question_imageUrl" to ""
                    )
                    // Contacts 컬렉션에 data를 자동 이름으로 저장
                    db.collection("Question")
                        .document(doc)
                        .set(data)
                        .addOnSuccessListener {

                            loadingAnimDialog.dismiss()

                            // 성공할 경우
                            Toast.makeText(this@Question_Write, "데이터가 추가되었습니다", Toast.LENGTH_SHORT)
                                .show()

                            finish()

                            //go_board2.putExtra("board_doc", it.toString())
                            // startActivity(go_board2)
                        }
                        .addOnFailureListener { exception ->
                            // 실패할 경우
                            Toast.makeText(this@Question_Write, "실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                }
            }

        }
    }
}