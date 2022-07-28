package com.example.cancer_prevention.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cigarette (
    val check : Boolean,
    var content: String,
    var Year : Int,
    var Month : Int,
    var Day : Int,
    var time : String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}