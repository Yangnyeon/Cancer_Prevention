package com.example.cancer_prevention.Bad_Food

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.Food.Food_Adapter
import com.example.cancer_prevention.Food.Food_model
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityBadFoodBinding
import com.example.cancer_prevention.databinding.ActivityRxJavaTranningBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main_bar.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.*
import kotlinx.android.synthetic.main.activity_main_bar_sub.view.*

class Bad_Food_Activity : AppCompatActivity() {

    lateinit var binding: ActivityBadFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBadFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingAnimDialog = loading_screen(this@Bad_Food_Activity)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        FirebaseFirestore.getInstance().collection("Bad_Food")
            .get()
            .addOnSuccessListener { result ->

                loadingAnimDialog.dismiss()

                for (document in result) {
                    val user = result.toObjects(Bad_Food_model::class.java)
                    binding.BADFoodRecyclerView.adapter = Bad_Food_Adapter(this@Bad_Food_Activity, user)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
            }

        binding.BADFoodRecyclerView.apply {
            layoutManager = GridLayoutManager(this@Bad_Food_Activity, 2)
        }

        setSupportActionBar(main_layout_toolbar_sub) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

        main_layout_toolbar_sub.go_finish.setOnClickListener {
            finish()
        }

    }
}