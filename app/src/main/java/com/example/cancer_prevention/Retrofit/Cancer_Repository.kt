package com.example.cancer_prevention.Retrofit

import android.app.Application
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.POST
import javax.inject.Inject

class Cancer_Repository @Inject constructor() {


    private val Cancer_API: Cancer_APi

    init {
        var db = Cancer_module.getPost()
        Cancer_API = db!!
    }

    suspend fun getPost(perpage : Int, per : Int, current : Int) : Response<Cancer_data_class> {

        return Cancer_API.getAlbums(perpage,per,current)
    }



    /*
    suspend fun getPost(perpage : Int, per : Int, current : Int) : Response<Cancer_data_class> {

        return Cancer_Instance.api.getAlbums(perpage,per,current)
    }

     */



    /*
    suspend fun makeApiCall(perpage : Int, per : Int, current : Int, liveDataList: MutableLiveData<List<Cancer_data_class>>) {
        val call: Call<Cancer_data_class> = Cancer_API.getAlbums(perpage,per,current)

        call?.enqueue(object : Callback<Cancer_data_class> {
            override fun onFailure(call: Call<Cancer_data_class>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(call: Call<Cancer_data_class>, response: Response<Cancer_data_class>) {
                liveDataList.postValue(response.body()?.items!!)
            }
        })

    }

     */

}