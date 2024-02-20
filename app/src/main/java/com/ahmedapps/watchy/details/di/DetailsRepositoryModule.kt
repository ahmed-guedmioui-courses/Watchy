package com.ahmedapps.watchy.details.di

import com.ahmedapps.watchy.details.data.repository.MediaDetailsRepositoryImpl
import com.ahmedapps.watchy.details.data.repository.SimilarMediaRepositoryImpl
import com.ahmedapps.watchy.details.domain.repository.MediaDetailsRepository
import com.ahmedapps.watchy.details.domain.repository.SimilarMediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(
        mediaDetailsRepositoryImpl: MediaDetailsRepositoryImpl
    ): MediaDetailsRepository

    @Binds
    @Singleton
    abstract fun bindSimilarMediaRepository(
        similarMediaRepositoryImpl: SimilarMediaRepositoryImpl
    ): SimilarMediaRepository

}
