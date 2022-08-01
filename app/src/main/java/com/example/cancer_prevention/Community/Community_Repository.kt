package com.example.cancer_prevention.Community

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cancer_prevention.room.Cigarette
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlin.coroutines.coroutineContext

class Community_Repository(application: Application) {

    val db = FirebaseFirestore.getInstance()

    val itemList = arrayListOf<ListLayout>()

    fun get_Community(): LiveData<List<ListLayout>> {
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
                //adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("MainActivity", "Error getting documents: $exception")
            }

        return get_Community()
    }

}