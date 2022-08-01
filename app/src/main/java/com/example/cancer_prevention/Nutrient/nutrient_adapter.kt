package com.example.cancer_prevention.Nutrient

import android.app.Application
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityNutrientScreenBinding
import com.example.cancer_prevention.databinding.CancerNutrientBinding
import com.example.cancer_prevention.databinding.TodoItemBinding
import com.example.cancer_prevention.room.Cigarette
import com.example.cancer_prevention.room.CigaretteAdapter
import com.example.cancer_prevention.room.OnItemClick
import com.example.cancer_prevention.room.TodoViewModel

class nutrient_adapter(val context: Context, var nutrient_items: List<nutrient_model>) : RecyclerView.Adapter<nutrient_adapter.TodoViewHolder>() {

    //private val nutrient_items = ArrayList<nutrient_model>()

    private var nutrient_memoList = emptyList<nutrient_model>()

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