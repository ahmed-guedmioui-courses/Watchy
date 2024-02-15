package com.ahmedapps.watchy.auth.di

import com.ahmedapps.watchy.auth.data.remote.AuthApi
import com.ahmedapps.watchy.auth.domain.usecase.FormValidatorUseCase
import com.ahmedapps.watchy.auth.domain.usecase.ValidateEmailUseCase
import com.ahmedapps.watchy.auth.domain.usecase.ValidateNameUseCase
import com.ahmedapps.watchy.auth.domain.usecase.ValidatePasswordUseCase
import com.ahmedapps.watchy.util.BackendConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BackendConstants.BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFormValidatorUseCase(): FormValidatorUseCase {
        return FormValidatorUseCase(
            validName = ValidateNameUseCase(),
            validEmail = ValidateEmailUseCase(),
            validPassword = ValidatePasswordUseCase(),
        )
    }

}























