package com.example.cancer_prevention.Retrofit

import retrofit2.Response
import retrofit2.http.POST

class Cancer_Repository {

    suspend fun getPost(perpage : Int, per : Int, current : Int) : Response<Cancer_data_class> {
        //return Cancer_Instance.api.getAlbums(50, 1, 25)
        return Cancer_Instance.api.getAlbums(perpage,per,current)
    }


}