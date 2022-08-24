package com.example.cancer_prevention.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentCancerBadFoodBinding
import com.example.cancer_prevention.databinding.FragmentCommunityBinding


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