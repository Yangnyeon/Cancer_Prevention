package com.example.cancer_prevention.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.cancer_prevention.Retrofit.data
import kotlinx.coroutines.flow.Flow

class CigaretteRepository(application: Application) {
    private val cigaretteDao: CigaretteDao
    private val cigaretteList: LiveData<List<Cigarette>>

    init {
        var db = AppDatabase.getInstance(application)
        cigaretteDao = db!!.todoDao()
        cigaretteList = db.todoDao().getAll()
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