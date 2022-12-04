package com.example.cancer_prevention.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.cancer_prevention.Retrofit.data
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CigaretteRepository @Inject

constructor(application: Application) {


    private val cigaretteDao: CigaretteDao
    init {
        var db = Cigarette_Module.get_Cigarette_DB(application)
        cigaretteDao = db!!.todoDao()
    }

    fun insert(cigarette: Cigarette) {
        cigaretteDao.insert(cigarette)
    }

    fun updateMemo(cigarette: Cigarette){
        cigaretteDao.update(cigarette)
    }

    fun delete(cigarette: Cigarette){
        cigaretteDao.delete(cigarette)
    }

    fun getAll(): LiveData<List<Cigarette>> {
        return cigaretteDao.getAll()
    }

    fun readDateData(year : Int, month : Int, day : Int): Flow<List<Cigarette>> {
        return cigaretteDao.readDateData(year, month, day)
    }


    fun searchDatabase(searchQuery: String): Flow<List<Cigarette>> {
        return cigaretteDao.searchDatabase(searchQuery)
    }
}