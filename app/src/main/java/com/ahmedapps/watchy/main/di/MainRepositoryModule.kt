package com.ahmedapps.watchy.main.di

import com.ahmedapps.watchy.main.data.repository.MainRepositoryImpl
import com.ahmedapps.watchy.main.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        mediaRepositoryImpl: MainRepositoryImpl
    ): MainRepository

}
