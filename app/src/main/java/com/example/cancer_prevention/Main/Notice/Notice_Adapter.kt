package com.example.cancer_prevention.Main.Notice

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Community.Community_holder
import com.example.cancer_prevention.Community.ListLayout
import com.example.cancer_prevention.R
import com.google.firebase.firestore.FieldValue

class Notice_Adapter(val itemList: ArrayList<Notice_Layout>, val context: Context): RecyclerView.Adapter<Notice_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notice_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Notice_Adapter.ViewHolder, position: Int) {
        holder.notice_name.text = itemList[position].notice_name
        holder.notice_date.text = itemList[position].notice_date
        var photo= itemList[position]
        holder.bind(photo)

        holder.itemView.setOnClickListener {


            var Notice_doc = itemList[position].notice_doc

            val go_board = Intent(context, Notice_holder::class.java)
            go_board.putExtra("Notice_doc", Notice_doc)
            context.startActivity(go_board)

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val notice_name: TextView = itemView.findViewById(R.id.notice_name)
        val notice_date : TextView = itemView.findViewById(R.id.notice_date)
        var notice_image: ImageView = itemView.findViewById(R.id.notice_image)

        fun bind(listlayout:Notice_Layout){
            Glide.with(context).load(listlayout.notice_imageUrl).fallback(R.drawable.ic_baseline_add_a_photo_24)
                .error(R.drawable.ic_baseline_add_a_photo_24)
                .into(notice_image)
        }

    }


}