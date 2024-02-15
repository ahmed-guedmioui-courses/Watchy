package com.ahmedapps.watchy.favorites.di

import android.app.Application
import androidx.room.Room
import com.ahmedapps.watchy.favorites.data.local.FavoriteMediaDatabase
import com.ahmedapps.watchy.favorites.data.remote.BackendMediaApi
import com.ahmedapps.watchy.util.BackendConstants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FavoritesModule {

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideFavoriteMediaDatabase(app: Application): FavoriteMediaDatabase {
        return Room.databaseBuilder(
            app,
            FavoriteMediaDatabase::class.java,
            "favoriteMediaDB.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBackendMediaApi(): BackendMediaApi {
        return Retrofit.Builder()
            .baseUrl(BackendConstants.BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BackendMediaApi::class.java)
    }

}









