package com.ahmedapps.watchy.profile.di

import com.ahmedapps.watchy.profile.data.repository.ProfileRepositoryImpl
import com.ahmedapps.watchy.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        accountRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

}