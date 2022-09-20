package com.example.cancer_prevention.Retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Cancer_ViewModel_Factory(private val repository : Cancer_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Cancer_VIewModel(repository) as T
    }
}