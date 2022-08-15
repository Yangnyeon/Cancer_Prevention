package com.example.cancer_prevention.Main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.cancer_prevention.Nutrient.nutrient_screen
import com.example.cancer_prevention.R
import com.example.cancer_prevention.Retrofit.Cancer_Retrofit
import com.example.cancer_prevention.databinding.FragmentMainBinding
import com.example.cancer_prevention.room.Calendar.Calendar_Room
import com.example.cancer_prevention.room.Cancer_Room
import com.example.cancer_prevention.room.Room_Activity
import com.example.cancer_prevention.room.liver_Screen
import com.google.common.reflect.Reflection.getPackageName
import kotlinx.android.synthetic.main.cancer_nutrient.*
import kotlinx.android.synthetic.main.fragment_main_.*
import java.lang.Package.getPackage
import java.lang.Package.getPackages
import android.view.WindowManager

import android.view.Display

import android.util.AttributeSet

import android.widget.VideoView
import android.util.DisplayMetrics
import android.media.MediaPlayer

import android.media.MediaPlayer.OnVideoSizeChangedListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.LinearLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


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

        binding.SelectCigaretteCalendar.setOnClickListener {
            startActivity(Intent(requireActivity(), Calendar_Room::class.java))
        }

        binding.NutrientGogo.setOnClickListener {
            startActivity(Intent(requireActivity(), nutrient_screen::class.java))
        }

        binding.gogoRetrofit2.setOnClickListener {
            startActivity(Intent(requireActivity(), Cancer_Room::class.java))
        }

        binding.SelectCigaretteGan.setOnClickListener {
            startActivity(Intent(requireActivity(), liver_Screen::class.java))
        }

        binding.SelectGogo2.setOnClickListener {
            startActivity(Intent(requireActivity(), Rx_java_tranning::class.java))
        }

        val adapter11 = ViewPagerAdapter()
        binding.viewpager11.adapter = adapter11

        val thread = Thread(PagerRunnable())
        thread.start()

        var bounce = AnimationUtils.loadAnimation(requireActivity(),R.anim.bounce)

        val interpolator = BounceInterpolator()

        bounce.interpolator = interpolator
        bounce.repeatCount = Animation.INFINITE
        bounce.repeatMode = Animation.REVERSE
        bounce.fillAfter = true

        /*
        runBlocking {
            var gogo1 = GlobalScope.launch {
                delay(1000L)
                binding.cancerImage1.startAnimation(bounce)
            }

            var gogo2 = GlobalScope.launch {
                delay(3000L)
                binding.cancerImage2.startAnimation(bounce)
            }

            var gogo3 = GlobalScope.launch {
                delay(5000L)
                binding.cancerImage3.startAnimation(bounce)
            }

            gogo1.join()
            gogo2.join()
            gogo3.join()
        }

         */




        binding.cancerImage1.startAnimation(bounce)

        binding.cancerImage2.startAnimation(bounce)

        binding.cancerImage3.startAnimation(bounce)





        /*

        val videoUri = Uri.parse("//player.vimeo.com/progressive_redirect/playback/733046618/rendition/720p/file.mp4?loc=external&oauth2_token_id=1027659655&signature=8b65f7302dcc93562a2aea90f4e62f384e293827996cabe1cc0602a6414537fb")
        binding.videoView.setVideoURI(videoUri);
        binding.videoView.start();
        binding.videoView.setOnCompletionListener {
            binding.videoView.start();
        }
        binding.videoView.setOnPreparedListener {
            it.isLooping
        }

         */

        //val VIDEO_PATH = "android.resource://" + packageName     + "/" + R.raw.cancer_video

        /*

        val VIDEO_PATH = "android.resource://" + requireActivity().packageName + "/" + R.raw.cancer_video3

        var uri: Uri = Uri.parse(VIDEO_PATH)
        binding.videoView.setVideoURI(uri)
        binding.videoView.requestFocus()
        binding.videoView.start()
        binding.videoView.setMediaController(MediaController(requireActivity()))

        binding.videoView.setOnCompletionListener {
            binding.videoView.start()
            it.isLooping
        }

        binding.videoView.setOnPreparedListener {
            binding.videoView.setMediaController(null)
            binding.videoView.start()     // 동영상 재개
            it.isLooping
        }

        val onVideoSizeChangedListener =
            OnVideoSizeChangedListener { mp, width, height ->
                val lp = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                binding.videoView.layoutParams = lp
            }

        onVideoSizeChangedListener

         */

        //



        //

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


    internal class MyVideoView(context: Context?, attrs: AttributeSet?) :
        VideoView(context, attrs) {
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val dis =
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            setMeasuredDimension(dis.width, dis.height)
        }
    }





}