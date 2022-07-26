package com.example.cancer_prevention.Nutrient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentNutrientBottomSheetBinding
import com.example.cancer_prevention.databinding.FragmentNutritnerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_community_holder.*
import kotlinx.android.synthetic.main.fragment_nutrient__bottom__sheet.*


class Nutrient_Bottom_Sheet : BottomSheetDialogFragment() {

    private var _binding : FragmentNutrientBottomSheetBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutrientBottomSheetBinding.inflate(inflater, container, false)



        val extra = arguments

        var doc = extra?.getString("Nutrient_Doc")
        var nutrinent_content = extra?.getString("Nutrient_Content")


        db.collection("Nutrient")
            .document(doc.toString())
            .get()
            .addOnSuccessListener { result ->
                try {
                    with(result) {
                        binding.nutrientText.text = nutrinent_content.toString()
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