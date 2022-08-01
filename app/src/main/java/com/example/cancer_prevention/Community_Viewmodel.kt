package com.example.cancer_prevention

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cancer_prevention.Community.Community_Repository
import com.example.cancer_prevention.Community.ListLayout
import com.example.cancer_prevention.room.Cigarette
import com.example.cancer_prevention.room.CigaretteRepository

class Community_Viewmodel(application: Application) : AndroidViewModel(application) {
    private val Community_repository = Community_Repository(application)
    private val items = Community_repository.get_Community()


    private var _Community_Data = MutableLiveData<List<ListLayout>>()

    fun get_Community(): LiveData<List<ListLayout>> {
        return items
    }
}