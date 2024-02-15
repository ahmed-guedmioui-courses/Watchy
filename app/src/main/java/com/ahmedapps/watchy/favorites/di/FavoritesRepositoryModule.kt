package com.ahmedapps.watchy.favorites.di

import com.ahmedapps.watchy.favorites.data.repository.BackendFavoriteMediaRepositoryImpl
import com.ahmedapps.watchy.favorites.data.repository.FavoritesRepositoryImpl
import com.ahmedapps.watchy.favorites.domain.repository.BackendFavoriteMediaRepository
import com.ahmedapps.watchy.favorites.domain.repository.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteMediaRepository(
        favoriteMediaRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository

    @Binds
    @Singleton
    abstract fun bindBackendFavoriteMediaRepository(
        backendFavoriteMediaRepositoryImpl: BackendFavoriteMediaRepositoryImpl
    ): BackendFavoriteMediaRepository

}
