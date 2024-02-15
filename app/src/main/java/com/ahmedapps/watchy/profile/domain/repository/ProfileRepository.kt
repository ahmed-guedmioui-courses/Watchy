package com.ahmedapps.watchy.profile.domain.repository

interface ProfileRepository {

    suspend fun getName(): String
    suspend fun getEmail(): String

}










