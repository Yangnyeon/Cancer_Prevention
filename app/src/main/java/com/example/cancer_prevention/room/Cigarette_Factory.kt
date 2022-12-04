package com.example.cancer_prevention.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Cigarette_Factory(private val repository : CigaretteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(repository) as T
    }
}
