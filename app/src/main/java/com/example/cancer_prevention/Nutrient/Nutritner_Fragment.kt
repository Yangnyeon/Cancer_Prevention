package com.example.cancer_prevention.Nutrient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cancer_prevention.MainActivity
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentNutritnerBinding
import com.example.cancer_prevention.databinding.FragmentQuestionCommunityBinding
import com.google.firebase.firestore.FirebaseFirestore


class Nutritner_Fragment : Fragment() {

    private var _binding : FragmentNutritnerBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutritnerBinding.inflate(inflater, container, false)


            db.collection("Nutrient")
            .get()
            .addOnSuccessListener {
                    result ->
                for(document in result) {
                    val user = result.toObjects(nutrient_model::class.java)
                    binding.NutrientRecyclerView.adapter =
                        fragmentManager?.let { nutrient_adapter(requireActivity(),user, it) }

                    //  binding.NutrientRecyclerView.adapter = nutrient_adapter(requireActivity(),user, fragmentManager)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "실패", Toast.LENGTH_SHORT).show()
            }


        binding.NutrientRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }

        binding.goHome.setOnClickListener {
            var main_activity = MainActivity()
            main_activity = activity as MainActivity
            main_activity.onFragmentChanged(0)
        }


        return binding.root
    }

}