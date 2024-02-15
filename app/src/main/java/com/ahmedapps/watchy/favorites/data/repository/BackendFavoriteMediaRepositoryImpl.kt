package com.ahmedapps.watchy.favorites.data.repository

import android.content.SharedPreferences
import com.ahmedapps.watchy.favorites.data.remote.BackendMediaApi
import com.ahmedapps.watchy.favorites.data.remote.request.MediaByIdRequest
import com.ahmedapps.watchy.favorites.data.remote.request.MediaRequest
import com.ahmedapps.watchy.favorites.data.remote.request.UpsertMediaRequest
import com.ahmedapps.watchy.favorites.data.remote.responde.BackendMediaDto
import com.ahmedapps.watchy.favorites.domain.repository.BackendFavoriteMediaRepository
import com.ahmedapps.watchy.util.APIConstants
import com.google.gson.JsonIOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class BackendFavoriteMediaRepositoryImpl @Inject constructor(
    private val backendMediaApi: BackendMediaApi,
    private val prefs: SharedPreferences,
) : BackendFavoriteMediaRepository {

    override suspend fun getLikedMediaItemList(): List<BackendMediaDto>? {

        val email = prefs.getString("email", null)
        if (email.isNullOrBlank()) {
            return null
        }

        return try {
            backendMediaApi.getLikedMediaItemList(email)
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }

    }

    override suspend fun getBookmarkedMediaItemList(): List<BackendMediaDto>? {

        val email = prefs.getString("email", null)
        if (email.isNullOrBlank()) {
            return null
        }

        return try {
            backendMediaApi.getBookmarkedMediaItemList(email)
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }

    }

    override suspend fun getMediaById(mediaId: Int): BackendMediaDto? {

        val email = prefs.getString("email", null)
        if (email.isNullOrBlank()) {
            return null
        }

        return try {
            backendMediaApi.getMediaById(
                mediaId = mediaId.toString(), email = email
            )
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }

    }

    override suspend fun upsertMediaToUser(
        mediaRequest: MediaRequest
    ): Boolean {

        val email = prefs.getString("email", null)
        if (email.isNullOrBlank()) {
            return false
        }

        return try {
            backendMediaApi.upsertMediaToUser(
                UpsertMediaRequest(
                    mediaRequest = mediaRequest, email = email
                )
            )
            true

        } catch (e: HttpException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteMediaFromUser(mediaId: Int): Boolean {

        Timber.tag(APIConstants.GET_TAG).d("deleteMediaFromUser()")

        val email = prefs.getString("email", null)
        if (email.isNullOrBlank()) {
            return false
        }

        return try {
            backendMediaApi.deleteMediaFromUser(
                MediaByIdRequest(
                    mediaId = mediaId, email = email
                )
            )
            true

        } catch (e: HttpException) {
            false
        } catch (e: JsonIOException) {
            // returning true because everything works as expected,
            // the media is deleted from the backend as expected,
            // but this then this exception is thrown for some reason.
            true
        } catch (e: Exception) {
            false
        }

    }

}


















