package com.example.cancer_prevention.Community

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityCommunityWriteBinding
import com.example.cancer_prevention.databinding.FragmentCommunityBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Community_Fragment : Fragment() {


    private var _binding : FragmentCommunityBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!
    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열

    val ref : CollectionReference = db.collection("Contacts")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)

        val adapter123 = ListAdapter(itemList, requireActivity())   // 리사이클러 뷰 어댑터

        binding.btnRead.performClick()

        binding.rvList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter123


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


        /* binding.btnWrite.setOnClickListener {
             // 대화상자 생성
             val builder = AlertDialog.Builder(requireActivity())

             val currentTime : Long = System.currentTimeMillis()
             val simpleDate = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
             val mDate: Date = Date(currentTime)
             val getTime = simpleDate.format(mDate)

             val doc = UUID.randomUUID().toString()

             // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
             val tvName = TextView(requireActivity())
             tvName.text = "제목 입력"

             val tvNumber = TextView(requireActivity())
             tvNumber.text = "내용 입력"

             val passNumber = TextView(requireActivity())
             passNumber.text = "비밀번호 입력"

             val Nickname = TextView(requireActivity())
             Nickname.text = "닉네임 입력"

             val etName = EditText(requireActivity())
             etName.isSingleLine = true

             val etNumber = EditText(requireActivity())
             etNumber.isSingleLine = true

             val password = EditText(requireActivity())
             password.isSingleLine = true

             val Nickname_edit = EditText(requireActivity())
             Nickname_edit.isSingleLine = true


             val mLayout = LinearLayout(requireActivity())
             mLayout.orientation = LinearLayout.VERTICAL
             mLayout.setPadding(16)
             mLayout.addView(tvName)
             mLayout.addView(etName)
             mLayout.addView(tvNumber)
             mLayout.addView(etNumber)
             mLayout.addView(passNumber)
             mLayout.addView(password)
             mLayout.addView(Nickname)
             mLayout.addView(Nickname_edit)
             builder.setView(mLayout)

             builder.setTitle("데이터 추가")
             builder.setPositiveButton("추가") { dialog, which ->
                 // EditText에서 문자열을 가져와 hashMap으로 만듦
                 val data = hashMapOf(
                     "name" to etName.text.toString(),
                     "number" to etNumber.text.toString(),
                     "com_date" to getTime.toString(),
                     "password" to password.text.toString(),
                     "doc" to doc,
                     "nickname" to Nickname_edit.text.toString(),
                     "liked" to 0.toString()
                 )
                 // Contacts 컬렉션에 data를 자동 이름으로 저장
                 db.collection("Contacts")
                     .document(doc)
                     .set(data)
                     .addOnSuccessListener {
                         // 성공할 경우
                         Toast.makeText(requireActivity(), "데이터가 추가되었습니다", Toast.LENGTH_SHORT).show()

                         //go_board2.putExtra("board_doc", it.toString())
                         // startActivity(go_board2)
                     }
                     .addOnFailureListener { exception ->
                         // 실패할 경우
                         Log.w("MainActivity", "Error getting documents: $exception")
                     }

                 binding.btnRead.performClick()
             }
             builder.setNegativeButton("취소") { dialog, which ->

             }
             builder.show()
         }

         */
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