package com.example.cancer_prevention.Nutrient

import android.app.Application
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Community.Community_holder
import com.example.cancer_prevention.R
import com.example.cancer_prevention.Retrofit.data
import com.example.cancer_prevention.databinding.ActivityNutrientScreenBinding
import com.example.cancer_prevention.databinding.CancerNutrientBinding
import com.example.cancer_prevention.databinding.FragmentNutritnerBinding
import com.example.cancer_prevention.databinding.TodoItemBinding
import com.example.cancer_prevention.room.Cigarette
import com.example.cancer_prevention.room.CigaretteAdapter
import com.example.cancer_prevention.room.OnItemClick
import com.example.cancer_prevention.room.TodoViewModel
import okio.blackholeSink

class nutrient_adapter(val context: Context, var nutrient_items: List<nutrient_model>) : RecyclerView.Adapter<nutrient_adapter.TodoViewHolder>() {

    //private val nutrient_items = ArrayList<nutrient_model>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CancerNutrientBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return nutrient_items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(nutrient_items[position])

        holder.itemView.setOnClickListener {
            val Nutrinet_bottom_sheet = Nutrient_Bottom_Sheet()

            var Nutrient_doc_bundle = bundleOf("Nutrient_Doc" to nutrient_items[position].Nutrient_Doc,
            "Nutrient_Content" to nutrient_items[position].Nutrient_Content)

            Nutrinet_bottom_sheet.arguments = Nutrient_doc_bundle

            Nutrinet_bottom_sheet.show((context as nutrient_screen).supportFragmentManager, Nutrinet_bottom_sheet.tag)
        }

    }


    inner class TodoViewHolder(private val binding: CancerNutrientBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: nutrient_model){

            val uploadCurrent: nutrient_model = nutrient_items[position]

            binding.nutrientHolerName.text = item.Nutrient_Name

            Glide.with(context)
                .load(uploadCurrent.Nutrient_Image)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(binding.foodHolderImage)
        }
    }

    fun setData(data : List<nutrient_model>){
        nutrient_items = data
        notifyDataSetChanged()

    }


    //

}