package com.example.cancer_prevention.Retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.CancerHolderBinding

class Cancer_adapter(var myList: List<data> = emptyList<data>()) : RecyclerView.Adapter<Cancer_adapter.MyViewHolder>(){


    class MyViewHolder(val binding: CancerHolderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cancer_adapter.MyViewHolder {
        val binding = CancerHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Cancer_adapter.MyViewHolder, position: Int) {
        holder.binding.CancerHolder1.text = myList[position].Cancer_number
        holder.binding.CancerHolder2.text = myList[position].Cancer_number2
        holder.binding.CancerHolder3.text = myList[position].Cancer_number3
        holder.binding.CancerHolder4.text = myList[position].Cancer_number4
        holder.binding.CancerHolder5.text = myList[position].Cancer_number5
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(newList : List<data>){
        myList = newList
        // 새로고침
        notifyDataSetChanged()
    }

    class ViewHolder (itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val Cancer_holer1 = itemView?.findViewById<TextView>(R.id.Cancer_holder1)
        val Cancer_holer2 = itemView?.findViewById<TextView>(R.id.Cancer_holder2)
        val Cancer_holer3 = itemView?.findViewById<TextView>(R.id.Cancer_holder3)
        val Cancer_holer4 = itemView?.findViewById<TextView>(R.id.Cancer_holder4)
        val Cancer_holer5 = itemView?.findViewById<TextView>(R.id.Cancer_holder5)
    }
}