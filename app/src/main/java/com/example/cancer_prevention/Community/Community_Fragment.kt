package com.example.cancer_prevention.Community

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.Community_Viewmodel
import com.example.cancer_prevention.Nutrient.nutrient_viewmodel
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityCommunityWriteBinding
import com.example.cancer_prevention.databinding.FragmentCommunityBinding
import com.example.cancer_prevention.room.TodoViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Community_Fragment : Fragment() {


    private var _binding : FragmentCommunityBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!
    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열

    val ref : CollectionReference = db.collection("Contacts")

    //test




    private lateinit var model: Community_Viewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)

        val adapter123 = ListAdapter(itemList, requireActivity())   // 리사이클러 뷰 어댑터

        val loadingAnimDialog = loading_screen(requireActivity())

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        loadingAnimDialog.show()

        binding.btnRead.performClick()

        binding.rvList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter123

        /*test

        model = ViewModelProvider(this, Community_Factory(requireActivity().application))
            .get(Community_Viewmodel::class.java)

        model.get_Community().observe(requireActivity(), Observer{
            adapter123.setList(it)
            adapter123.notifyDataSetChanged()
        })

         */

        //




        db.collection("Contacts") // 작업할 컬렉션
            .orderBy("com_date", Query.Direction.DESCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우

                loadingAnimDialog.dismiss()

                itemList.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item =
                        ListLayout(document["name"] as String, document["number"] as String,
                            document["com_date"] as String?, document["password"] as String,
                            document["doc"] as String, document["nickname"] as String, document["liked"] as Long, document["eye_count"] as Long)
                    itemList.add(item)
                }
                adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("MainActivity", "Error getting documents: $exception")
            }


        binding.btnRead.setOnClickListener {
            db.collection("Contacts")   // 작업할 컬렉션
                .orderBy("com_date", Query.Direction.DESCENDING)
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item =
                            ListLayout(document["name"] as String, document["number"] as String, document["com_date"] as String?, document["password"] as String,document["doc"] as String, document["nickname"] as String, document["liked"] as Long,  document["eye_count"] as Long)
                        itemList.add(item)
                    }
                    adapter123.notifyDataSetChanged()  // 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }

        binding.btnWrite.setOnClickListener {
            startActivity(Intent(requireActivity(), Community_Write::class.java))
        }

        return binding.root
    }

    fun update() {

        val adapter123 = ListAdapter(itemList, requireActivity())   // 리사이클러 뷰 어댑터

        db.collection("Contacts") // 작업할 컬렉션
            .orderBy("com_date", Query.Direction.DESCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item =
                        ListLayout(document["name"] as String, document["number"] as String,
                            document["com_date"] as String?, document["password"] as String,
                            document["doc"] as String, document["nickname"] as String, document["liked"] as Long,  document["eye_count"] as Long)
                    itemList.add(item)
                }
                adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("MainActivity", "Error getting documents: $exception")
            }


    }

}