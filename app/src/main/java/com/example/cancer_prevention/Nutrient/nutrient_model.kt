package com.example.cancer_prevention.Nutrient

data class nutrient_model(val Nutrient_Image : String , val Nutrient_Name : String, val Nutrient_Doc : String, val Nutrient_Content : String)
{
    constructor() : this(
        "",
        "",
        "",
        ""
    )

}