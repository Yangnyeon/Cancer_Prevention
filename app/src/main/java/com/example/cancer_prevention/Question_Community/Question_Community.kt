package com.example.cancer_prevention.Question_Community

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.Community.ListAdapter
import com.example.cancer_prevention.Community.ListLayout
import com.example.cancer_prevention.Community.loading_screen
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentCommunityBinding
import com.example.cancer_prevention.databinding.FragmentQuestionCommunityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Question_Community : Fragment() {

    private var _binding : FragmentQuestionCommunityBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!
    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    val Question_itemList = arrayListOf<Question_Layout>()    // 리스트 아이템 배열

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentQuestionCommunityBinding.inflate(inflater, container, false)

        binding.btnWrite.setOnClickListener {
            startActivity(Intent(requireActivity(), Question_Write::class.java))
        }

        CoroutineScope(Dispatchers.Main).launch {

            val Question_adapter = Question_Adapter(Question_itemList, requireActivity())   // 리사이클러 뷰 어댑터

            //

            binding.rvList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            binding.rvList.adapter = Question_adapter

            //

            val loadingAnimDialog = loading_screen(requireActivity())

            loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

            loadingAnimDialog.show()

            db.collection("Question") // 작업할 컬렉션
                .orderBy("Question_date", Query.Direction.DESCENDING)
                .get() // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우

                    loadingAnimDialog.dismiss()

                    Question_itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val Question_item =
                            Question_Layout(document["Question_name"] as String, document["Question_content"] as String,
                                document["Question_date"] as String, document["Question_password"] as String,
                                document[ "Question_doc"] as String, document["Question_nickname"] as String,
                                document["Question_liked"] as Long, document["Question_eye"] as Long, document["Question_imageUrl"] as String)
                        Question_itemList.add(Question_item)

                    }
                    Question_adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }

        binding.CommunitySearchview.setOnQueryTextListener(searchViewTextListener)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Question_update()
    }

    fun Question_update() {

        CoroutineScope(Dispatchers.Main).launch {

            val Question_adapter = Question_Adapter(Question_itemList, requireActivity())   // 리사이클러 뷰 어댑터

            //

            binding.rvList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            binding.rvList.adapter = Question_adapter

            //

            val loadingAnimDialog = loading_screen(requireActivity())

            loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

            loadingAnimDialog.show()

            db.collection("Question") // 작업할 컬렉션
                .orderBy("Question_date", Query.Direction.DESCENDING)
                .get() // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우

                    loadingAnimDialog.dismiss()

                    Question_itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val Question_item =
                            Question_Layout(document["Question_name"] as String, document["Question_content"] as String,
                                document["Question_date"] as String, document["Question_password"] as String,
                                document[ "Question_doc"] as String, document["Question_nickname"] as String,
                                document["Question_liked"] as Long, document["Question_eye"] as Long, document["Question_imageUrl"] as String)
                        Question_itemList.add(Question_item)

                    }
                    Question_adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }
    }

    private var searchViewTextListener: androidx.appcompat.widget.SearchView.OnQueryTextListener =
        object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {



                return true
            }


            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {

                CoroutineScope(Dispatchers.Main).launch {
                    if(s != null) {

                        val Question_adapter = Question_Adapter(Question_itemList, requireActivity())   // 리사이클러 뷰 어댑터

                        binding.rvList.layoutManager =
                            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                        binding.rvList.adapter = Question_adapter

                        db.collection("Question") // 작업할 컬렉션
                            .orderBy("Question_date", Query.Direction.DESCENDING)
                            .get() // 문서 가져오기
                            .addOnSuccessListener { result ->
                                Question_itemList.clear()
                                for (document in result) {  // 가져온 문서들은 result에 들어감
                                    if (document.getString("Question_name").toString()!!.contains(s)) {
                                        var Question_item =
                                            Question_Layout(
                                                document["Question_name"] as String,
                                                document["Question_content"] as String,
                                                document["Question_date"] as String,
                                                document["Question_password"] as String,
                                                document[ "Question_doc"] as String,
                                                document["Question_nickname"] as String,
                                                document["Question_liked"] as Long,
                                                document["Question_eye"] as Long,
                                                document["Question_imageUrl"] as String
                                            )
                                        Question_itemList.add(Question_item)
                                    }
                                }
                                Question_adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
                            }
                            .addOnFailureListener { exception ->
                                // 실패할 경우z
                                Log.w("MainActivity", "Error getting documents: $exception")
                            }
                    } else if(s == "") {
                        onResume()
                    }
                }




                return true
            }
        }

}