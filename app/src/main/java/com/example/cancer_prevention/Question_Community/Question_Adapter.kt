package com.example.cancer_prevention.Question_Community

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Community.Community_holder
import com.example.cancer_prevention.Community.ListAdapter
import com.example.cancer_prevention.Community.ListLayout
import com.example.cancer_prevention.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Question_Adapter(val itemList: ArrayList<Question_Layout>, val context: Context): RecyclerView.Adapter<Question_Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Question_Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Question_Adapter.ViewHolder, position: Int) {
        holder.name.text = itemList[position].Question_name
        holder.content.text = itemList[position].Question_content
        holder.community_date.text = itemList[position].Question_date
        holder.nickname.text = itemList[position].Question_nickname
        holder.like_count.text = itemList[position].Question_liked.toString()
        holder.eye_count.text = itemList[position].Question_eye.toString()
        var photo= itemList[position]
        holder.bind(photo)

        val db = FirebaseFirestore.getInstance()

        db.collection("Question")
            .document(itemList[position].Question_Doc)
            .collection("Question_Comment")// 작업할 컬렉션
            .orderBy("Question_Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                holder.comment_count.text = result.size().toString()
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("TAG", "Error getting documents: $exception")
            }

        holder.itemView.setOnClickListener {

            var doc = itemList[position].Question_Doc


            val Question_go_board = Intent(context, Question_holder::class.java)
            Question_go_board.putExtra("Question_board_doc", doc)


            db.collection("Question")
                .document(doc)
                .update("Question_eye", FieldValue.increment(+1))
                .addOnSuccessListener { result ->
                }
                .addOnFailureListener { exception ->
                }

            context.startActivity(Question_go_board)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.list_tv_name)
        val content : TextView = itemView.findViewById(R.id.list_tv_content)
        val community_date : TextView = itemView.findViewById(R.id.list_tv_date)
        val comment_count = itemView.findViewById<TextView>(R.id.comment_count)
        val like_count = itemView.findViewById<TextView>(R.id.thumb_count)
        val nickname = itemView.findViewById<TextView>(R.id.list_tv_nickname)
        val eye_count = itemView.findViewById<TextView>(R.id.eye_count)

        var imageIv: ImageView = itemView.findViewById(R.id.list_image)

        fun bind(listlayout:Question_Layout){
            Glide.with(context).load(listlayout.Question_imageUrl).fallback(R.drawable.ic_baseline_add_a_photo_24)
                .error(R.drawable.ic_baseline_add_a_photo_24)
                .into(imageIv)
        }

    }
}