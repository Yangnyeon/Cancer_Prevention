package com.example.cancer_prevention.Community

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.cancer_prevention.Community_Viewmodel


class Community_Factory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(aClass: Class<T>): T {
        return Community_Viewmodel(application) as T
    }
}