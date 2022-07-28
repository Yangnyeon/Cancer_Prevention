package com.example.cancer_prevention.Main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cancer_prevention.Retrofit.Cancer_Retrofit
import com.example.cancer_prevention.databinding.FragmentMainBinding
import com.example.cancer_prevention.room.Room_Activity
import kotlinx.android.synthetic.main.fragment_main_.*


class Main_Fragment : Fragment() {

    private var _binding : FragmentMainBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    var currentPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.gogoRetrofit.setOnClickListener {
            startActivity(Intent(requireActivity(), Cancer_Retrofit::class.java))
        }

        binding.gogoRoom.setOnClickListener {
            startActivity(Intent(requireActivity(), Room_Activity::class.java))
        }



        val adapter11 = ViewPagerAdapter()
        binding.viewpager11.adapter = adapter11

        val thread = Thread(PagerRunnable())
        thread.start()



        return binding.root
    }

    private fun setPage() {
        if(currentPosition == 3 ) {
            currentPosition = 0
        }
        if(viewpager11 != null) {
            viewpager11.setCurrentItem(currentPosition, true)
        }

        currentPosition += 1
    }

    inner class PagerRunnable : Runnable {
        override fun run() {
            while(true) {
                Thread.sleep(3000)
                handler.sendEmptyMessage(0)
            }
        }
    }

}