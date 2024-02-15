package com.ahmedapps.watchy.profile.data.repository

import android.app.Application
import android.content.SharedPreferences
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val application: Application,
    private val prefs: SharedPreferences
) : ProfileRepository {

    override suspend fun getName(): String {
        return prefs.getString("name", null)
            ?: application.getString(R.string.name)
    }

    override suspend fun getEmail(): String {
        return prefs.getString("email", null)
            ?: application.getString(R.string.email)
    }

}










