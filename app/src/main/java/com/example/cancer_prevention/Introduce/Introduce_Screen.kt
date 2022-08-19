package com.example.cancer_prevention.Introduce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cancer_prevention.R
import com.example.cancer_prevention.databinding.FragmentCommunityBinding
import com.example.cancer_prevention.databinding.FragmentIntroduceScreenBinding


class Introduce_Screen : Fragment() {


    private var _binding : FragmentIntroduceScreenBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntroduceScreenBinding.inflate(inflater, container, false)

        binding.goNotice.setOnClickListener {

        }


        return binding.root
    }

}