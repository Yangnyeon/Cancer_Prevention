package com.example.cancer_prevention.Retrofit

import com.example.cancer_prevention.BuildConfig.GOOGLE_API_KEY1
import com.example.cancer_prevention.BuildConfig.GOOGLE_API_SERVICE2
import com.example.cancer_prevention.room.Cigarette
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Cancer_APi {

    /*

    @GET(GOOGLE_API_KEY1)
    fun getInfo(
        @Query("perPage")PerPage:Int,
        @Query("page")Page:Int,
        @Query("currentCount")currentCount:Int,
        @Query("serviceKey")ServiceKey:String = GOOGLE_API_SERVICE2
    ): Call<Cancer_data_class>


     */

    @GET(GOOGLE_API_KEY1)
    suspend fun getAlbums(@Query("perPage")PerPage:Int,
                          @Query("page")Page:Int,
                          @Query("currentCount")currentCount:Int,
                          @Query("serviceKey")ServiceKey:String = GOOGLE_API_SERVICE2)
                          : Response<Cancer_data_class>

    @GET(GOOGLE_API_KEY1)
    suspend fun get_Retrofit(@Query("perPage")PerPage:Int,
                          @Query("page")Page:Int,
                          @Query("currentCount")currentCount:Int,
                          @Query("serviceKey")ServiceKey:String = GOOGLE_API_SERVICE2,
                          @Query("Search_text")Search_text:String)
                          : Response<Cancer_data_class>

    /*
    @Query("SELECT * FROM Cigarette WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC")
    fun Read_Retrofit(year : Int, month : Int, day : Int) : Flow<List<Cigarette>>

     */
}