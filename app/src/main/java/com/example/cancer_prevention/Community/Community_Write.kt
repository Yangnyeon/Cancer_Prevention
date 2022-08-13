package com.example.cancer_prevention.Community

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import com.example.cancer_prevention.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_community_write.*
import java.text.SimpleDateFormat
import java.util.*

class Community_Write : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    val itemList2 = arrayListOf<ListLayout>()

    //이미지 업로드

    lateinit var storage: FirebaseStorage

    private val IMAGE_PICK=1111

    var selectImage: Uri?=null


    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write)

        storage = FirebaseStorage.getInstance()


        input.setOnClickListener(View.OnClickListener {
            saveNote()
            finish()
        })


        image_upload.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK) //선택하면 무언가를 띄움. 묵시적 호출
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectImage = data?.data
            image_upload.setImageURI(selectImage)
        }
    }

    private fun saveNote() {
        val title: String = edit_text_title2.text.toString()
        val description: String = edit_text_description2.text.toString()
        val nickname: String = write_nickname.text.toString()
        val now = System.currentTimeMillis()
        val simpleDate = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
        val mDate = Date(now)
        val getTime = simpleDate.format(mDate)
        val password = comment_password.text.toString()

        val doc = UUID.randomUUID().toString()

        if (selectImage != null) {
            if (title.trim { it <= ' ' }.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                var fileName =
                    SimpleDateFormat("yyyyMMddHHmmss").format(Date()) // 파일명이 겹치면 안되기 떄문에 시년월일분초 지정
                storage.reference.child("image").child(fileName)
                    .putFile(selectImage!!)//어디에 업로드할지 지정
                    .addOnSuccessListener { taskSnapshot -> // 업로드 정보를 담는다
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { it ->
                            //var imageUrl=it.toString()
                            //var photo= Photo(textEt.text.toString(),imageUrl)

                            val data = hashMapOf(
                                "name" to title.toString(),
                                "number" to description.toString(),
                                "com_date" to getTime.toString(),
                                "password" to password.toString(),
                                "doc" to doc,
                                "nickname" to nickname.toString(),
                                "liked" to 0.toLong(),
                                "eye_count" to 0.toLong(),
                                "imageUrl" to it.toString()
                            )

                            db.collection("Contacts")
                                .document(doc)
                                .set(data)
                                .addOnSuccessListener {
                                    finish()
                                }
                                .addOnFailureListener { exception ->
                                    // 실패할 경우
                                    Toast.makeText(
                                        this@Community_Write,
                                        "실패하였습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
            }
        } else {
            if (title.trim { it <= ' ' }.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                val data = hashMapOf(
                    "name" to title.toString(),
                    "number" to description.toString(),
                    "com_date" to getTime.toString(),
                    "password" to password.toString(),
                    "doc" to doc,
                    "nickname" to nickname.toString(),
                    "liked" to 0.toLong(),
                    "eye_count" to 0.toLong(),
                    "imageUrl" to ""
                )
                // Contacts 컬렉션에 data를 자동 이름으로 저장
                db.collection("Contacts")
                    .document(doc)
                    .set(data)
                    .addOnSuccessListener {
                        // 성공할 경우
                        Toast.makeText(this@Community_Write, "데이터가 추가되었습니다", Toast.LENGTH_SHORT)
                            .show()

                        finish()

                        //go_board2.putExtra("board_doc", it.toString())
                        // startActivity(go_board2)
                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우
                        Toast.makeText(this@Community_Write, "실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }



}