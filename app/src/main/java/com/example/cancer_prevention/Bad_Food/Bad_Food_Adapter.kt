package com.example.cancer_prevention.Bad_Food

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Food.Food_Adapter
import com.example.cancer_prevention.Food.Food_Bottom_Sheet
import com.example.cancer_prevention.Food.Food_model
import com.example.cancer_prevention.Nutrient.nutrient_screen
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.CancerFoodBinding

class Bad_Food_Adapter(val context: Context, var Bad_Food_items: List<Bad_Food_model>) : RecyclerView.Adapter<Bad_Food_Adapter.TodoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CancerFoodBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Bad_Food_items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.bind(Bad_Food_items[position])

        holder.itemView.setOnClickListener {
            var Bad_Food_bottom_sheet = Bad_Food_BottomSheet()

            var Bad_Food_doc_bundle = bundleOf(
                "Bad_Food_Doc" to Bad_Food_items[position].Bad_Food_Doc,
                "Bad_Food_Content" to Bad_Food_items[position].Bad_Food_Content
            )

            Bad_Food_bottom_sheet.arguments = Bad_Food_doc_bundle

            Bad_Food_bottom_sheet.show((context as Bad_Food_Activity).supportFragmentManager, Bad_Food_bottom_sheet.tag)

        }

    }


    inner class TodoViewHolder(private val binding: CancerFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Bad_Food_model) {

            val uploadCurrent: Bad_Food_model = Bad_Food_items[position]

            binding.foodHolerName.text = item.Bad_Food_Name

            Glide.with(context)
                .load(uploadCurrent.Bad_Food_Image)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(binding.foodFoodHolderImage)
        }
    }
}