package com.example.cancer_prevention.Food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentFoodBottomSheetBinding
import com.example.cancer_prevention.databinding.FragmentNutrientBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore


class Food_Bottom_Sheet : BottomSheetDialogFragment() {

    private var _binding : FragmentFoodBottomSheetBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodBottomSheetBinding.inflate(inflater, container, false)

        val extra = arguments

        var doc = extra?.getString("Food_Doc")
        var Food_content = extra?.getString("Food_Content")


        db.collection("Food")
            .document(doc.toString())
            .get()
            .addOnSuccessListener { result ->
                try {
                    with(result) {
                        binding.FoodText.text = Food_content.toString()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_SHORT).show()
            }

        return binding.root
    }


}