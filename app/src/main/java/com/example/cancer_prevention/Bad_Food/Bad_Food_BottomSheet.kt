package com.example.cancer_prevention.Bad_Food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentBadFoodBottomSheetBinding
import com.example.cancer_prevention.databinding.FragmentCancerBadFoodBinding
import com.example.cancer_prevention.databinding.FragmentFoodBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore


class Bad_Food_BottomSheet: BottomSheetDialogFragment()  {

    private var _binding : FragmentBadFoodBottomSheetBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBadFoodBottomSheetBinding.inflate(inflater, container, false)

        val extra = arguments

        var Bad_Food_doc = extra?.getString("Bad_Food_Doc")
        var Bad_Food_content = extra?.getString("Bad_Food_Content")


        db.collection("Bad_Food")
            .document(Bad_Food_doc.toString())
            .get()
            .addOnSuccessListener { result ->
                try {
                    with(result) {
                        binding.BadFoodText.text = Bad_Food_content.toString()
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