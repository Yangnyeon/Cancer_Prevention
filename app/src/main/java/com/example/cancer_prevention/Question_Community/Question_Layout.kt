package com.example.cancer_prevention.Question_Community

data class Question_Layout(val Question_name: String = "", val Question_content: String = "", val Question_date: String = "", val Question_password: String = "", val Question_Doc: String = "", var Question_nickname : String = "", var Question_liked : Long = 0, var Question_eye : Long = 0,  var Question_imageUrl:String ?= null)