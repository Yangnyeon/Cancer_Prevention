package com.example.cancer_prevention.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cancer_prevention.Bad_Food.Bad_Food_Adapter
import com.example.cancer_prevention.Bad_Food.Bad_Food_model
import com.example.cancer_prevention.Food.Food_Adapter
import com.example.cancer_prevention.Food.Food_model
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentCancerBadFoodBinding
import com.example.cancer_prevention.databinding.FragmentCommunityBinding
import com.google.firebase.firestore.FirebaseFirestore


class Cancer_bad_food : Fragment() {


    private var _binding : FragmentCancerBadFoodBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCancerBadFoodBinding.inflate(inflater, container, false)



        return binding.root
    }

}