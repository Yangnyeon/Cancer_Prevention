package com.example.cancer_prevention.Community

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.Question_Community.Question_Adapter
import com.example.cancer_prevention.Question_Community.Question_Layout
import com.example.cancer_prevention.databinding.FragmentCommunityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Community_Fragment : Fragment() {


    private var _binding : FragmentCommunityBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!
    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열

    val Question_itemList = arrayListOf<Question_Layout>()    // 리스트 아이템 배열



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

        binding.rvList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter123

        CoroutineScope(Dispatchers.Main).launch {
            db.collection("Contacts") // 작업할 컬렉션
                //.limit(3)
                .orderBy("com_date", Query.Direction.DESCENDING)
                .get() // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    loadingAnimDialog.dismiss()

                    itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item =
                            ListLayout(
                                document["name"] as String,
                                document["number"] as String,
                                document["com_date"] as String,
                                document["password"] as String,
                                document["doc"] as String,
                                document["nickname"] as String,
                                document["liked"] as Long,
                                document["eye_count"] as Long,
                                document["imageUrl"] as String
                            )
                        itemList.add(item)
                    }
                    adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우z
                    Log.w("MainActivity", "Error getting documents: $exception")
                }

        }


        /*
        binding.btnRead.setOnClickListener {
            db.collection("Contacts")   // 작업할 컬렉션
                .orderBy("com_date", Query.Direction.DESCENDING)
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item =
                            ListLayout(document["name"] as String, document["number"] as String, document["com_date"] as String, document["password"] as String,document["doc"] as String, document["nickname"] as String, document["liked"] as Long,  document["eye_count"] as Long)
                        itemList.add(item)
                    }
                    adapter123.notifyDataSetChanged()  // 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }

         */

        binding.btnWrite.setOnClickListener {
            startActivity(Intent(requireActivity(), Community_Write::class.java))
        }


        binding.CommunitySearchview.setOnQueryTextListener(searchViewTextListener)

        /*
          R.id.menu_item1 -> Toast.makeText(requireActivity(), "1", Toast.LENGTH_SHORT).show()
                R.id.menu_item2 -> Toast.makeText(requireActivity(), "2", Toast.LENGTH_SHORT).show()
                R.id.menu_item3 -> Toast.makeText(requireActivity(), "3", Toast.LENGTH_SHORT).show()
         */


        return binding.root
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

                        val adapter123 = ListAdapter(itemList, requireActivity())

                        binding.rvList.layoutManager =
                            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                        binding.rvList.adapter = adapter123

                        db.collection("Contacts") // 작업할 컬렉션
                            .orderBy("com_date", Query.Direction.DESCENDING)
                            .get() // 문서 가져오기
                            .addOnSuccessListener { result ->
                                itemList.clear()
                                for (document in result) {  // 가져온 문서들은 result에 들어감
                                    if (document.getString("name").toString()!!.contains(s)) {
                                        var item123 =
                                            ListLayout(
                                                document["name"] as String,
                                                document["number"] as String,
                                                document["com_date"] as String,
                                                document["password"] as String,
                                                document["doc"] as String,
                                                document["nickname"] as String,
                                                document["liked"] as Long,
                                                document["eye_count"] as Long,
                                                document["imageUrl"] as String
                                            )
                                        itemList.add(item123)
                                    }
                                }
                                adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
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

    fun Free_Community_update() {

        val adapter123 = ListAdapter(itemList, requireActivity())   // 리사이클러 뷰 어댑터

        //

        binding.rvList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter123

        //

        val loadingAnimDialog = loading_screen(requireActivity())

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        loadingAnimDialog.show()

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
                            document["com_date"] as String, document["password"] as String,
                            document["doc"] as String, document["nickname"] as String, document["liked"] as Long, document["eye_count"] as Long, document["imageUrl"] as String)
                    itemList.add(item)

                }
                adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("MainActivity", "Error getting documents: $exception")
            }



    }


    override fun onResume() {
        super.onResume()
        Free_Community_update()
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



}


