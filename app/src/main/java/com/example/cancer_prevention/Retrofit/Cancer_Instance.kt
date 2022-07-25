package com.example.cancer_prevention.Retrofit

import com.example.cancer_prevention.Retrofit.Cancer_address.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Cancer_Instance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : Cancer_APi by lazy {
        retrofit.create(Cancer_APi::class.java)
    }

}