package com.example.cancer_prevention.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CigaretteDao {
    @Query("SELECT * FROM Cigarette ORDER BY id DESC")
    fun getAll(): LiveData<List<Cigarette>>

    /*
    @Query("SELECT * FROM Cigarette ORDER BY id DESC")
    fun selectList(page: Int, loadSize: Int): LiveData<List<Cigarette>>

     */

    @Insert
    fun insert(cigarette: Cigarette)

    @Update
    fun update(cigarette: Cigarette)

    @Delete
    fun delete(cigarette: Cigarette)

    @Query("SELECT * FROM Cigarette WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC")
    fun readDateData(year : Int, month : Int, day : Int) : Flow<List<Cigarette>>

    @Query("SELECT * FROM Cigarette ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readAllData() : Flow<List<Cigarette>>

    @Query("SELECT * FROM Cigarette WHERE content LIKE :searchQuery ORDER BY id DESC")
    fun searchDatabase(searchQuery : String) : Flow<List<Cigarette>>


}