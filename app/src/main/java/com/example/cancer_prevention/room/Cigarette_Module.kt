package com.example.cancer_prevention.room

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Cigarette_Module {

    companion object {
        @Singleton
        @Provides
        fun get_Cigarette_DB(context: Application): AppDatabase {
            return AppDatabase.getInstance(context)!!
        }

        @Singleton
        @Provides
        fun getCigaretteDao(Cigarettte_Database: AppDatabase): CigaretteDao {
            return Cigarettte_Database.todoDao()
        }
    }


}