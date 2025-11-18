package com.example.weatherreport.di

import android.content.Context
import com.example.weatherreport.data.AppDatabase
import com.example.weatherreport.data.CityDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideCityDao(appDatabase: AppDatabase) : CityDAO {
        return appDatabase.cityDao()
    }

    @Provides
    fun provideCityAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

}