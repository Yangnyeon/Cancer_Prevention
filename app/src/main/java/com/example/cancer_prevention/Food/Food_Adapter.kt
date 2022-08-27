package com.example.cancer_prevention.Food

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cancer_prevention.Main.Rx_java_tranning
import com.example.cancer_prevention.Nutrient.Nutrient_Bottom_Sheet
import com.example.cancer_prevention.Nutrient.nutrient_adapter
import com.example.cancer_prevention.Nutrient.nutrient_model
import com.example.cancer_prevention.Nutrient.nutrient_screen
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.ActivityRxJavaTranningBinding
import com.example.cancer_prevention.databinding.CancerFoodBinding
import com.example.cancer_prevention.databinding.CancerNutrientBinding

class Food_Adapter(val context: Context, var Food_items: List<Food_model>) : RecyclerView.Adapter<Food_Adapter.TodoViewHolder>() {

    //private val nutrient_items = ArrayList<nutrient_model>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CancerFoodBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Food_items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(Food_items[position])

        holder.itemView.setOnClickListener {
            var Food_bottom_sheet = Food_Bottom_Sheet()

            var Food_doc_bundle = bundleOf(
                "Food_Doc" to Food_items[position].Food_Doc,
                "Food_Content" to Food_items[position].Food_Content
            )

            Food_bottom_sheet.arguments = Food_doc_bundle

            Food_bottom_sheet.show((context as Rx_java_tranning).supportFragmentManager, Food_bottom_sheet.tag)

        }

    }


    inner class TodoViewHolder(private val binding: CancerFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Food_model) {

            val uploadCurrent: Food_model = Food_items[position]

            binding.foodHolerName.text = item.Food_Name

            Glide.with(context)
                .load(uploadCurrent.Food_Image)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(binding.foodFoodHolderImage)
        }
    }
}