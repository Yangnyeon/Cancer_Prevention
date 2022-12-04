package com.example.cancer_prevention.room

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository : CigaretteRepository) : AndroidViewModel(Application()) {


    /*
    private val repository = CigaretteRepository(application)
    private val items = repository.getAll()

     */

    private var _currentData = MutableLiveData<List<Cigarette>>()

    val currentData : LiveData<List<Cigarette>>
        get() = _currentData

    fun insert(cigarette: Cigarette) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(cigarette)
        }
    }

    fun delete(cigarette: Cigarette){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(cigarette)
        }
    }

    fun getAll(): LiveData<List<Cigarette>> {

        return repository.getAll()
    }

    /*

    fun getAll(): LiveData<List<Cigarette>> {
        return item
    }
     */

    fun updateMemo(cigarette : Cigarette){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemo(cigarette)
        }
    }


    fun readDateData(year : Int, month : Int, day : Int) : LiveData<List<Cigarette>> {
        /*
        viewModelScope.launch(Dispatchers.IO) {
            val tmp = repository.readDateData(year, month, day)
            _currentData.postValue(tmp)
        }
         */
        return repository.readDateData(year, month, day).asLiveData()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Cigarette>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

}