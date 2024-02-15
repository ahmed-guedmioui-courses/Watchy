package com.ahmedapps.watchy.categories.di

import com.ahmedapps.watchy.categories.data.repository.CategoriesRepositoryImpl
import com.ahmedapps.watchy.categories.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository

}
