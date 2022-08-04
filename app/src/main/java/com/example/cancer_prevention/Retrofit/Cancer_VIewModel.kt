package com.example.cancer_prevention.Retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cancer_prevention.room.Cigarette
import com.example.cancer_prevention.room.CigaretteRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.POST

class Cancer_VIewModel(private val repository : Cancer_Repository) : ViewModel() {

    val myResponse : MutableLiveData<Response<Cancer_data_class>> = MutableLiveData()

    fun getPost(Perapge : Int, per : Int, current : Int) {
        viewModelScope.launch {
            val response = repository.getPost(Perapge, per, current)
            myResponse.value = response
        }
    }
}
