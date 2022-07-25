package com.example.cancer_prevention.Retrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.POST

class Cancer_VIewModel(private val repository : Cancer_Repository) : ViewModel() {

    val myResponse : MutableLiveData<Response<data>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }
}