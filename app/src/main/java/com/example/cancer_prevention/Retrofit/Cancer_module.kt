package com.example.cancer_prevention.Retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Cancer_module {

    companion object {

        /*
        @Singleton
        @Provides
        fun getRetroServieInstance(retrofit: Retrofit): RetroServieInstance {
            return retrofit.create(RetroServieInstance::class.java)
        }

         */




        @Singleton
        @Provides
        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(Cancer_address.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun getPost() : Cancer_APi {

            //return Cancer_Instance.api.getAlbums(perpage,per,current)
            return getRetroInstance().create(Cancer_APi::class.java)
        }

    }

}