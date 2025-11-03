package com.example.shoppinglist.di

import android.content.Context
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.ShoppingDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideShoppingDao(appDatabase: AppDatabase) : ShoppingDAO {
        return appDatabase.shoppingDao()
    }

    @Provides
    fun provideShoppingAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

}