package com.example.cancer_prevention.Retrofit

import retrofit2.Response
import retrofit2.http.POST

class Cancer_Repository {

    suspend fun getPost() : Response<data> {
        return Cancer_Instance.api.getPost()
    }

}