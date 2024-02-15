package com.ahmedapps.watchy.favorites.domain.repository

import com.ahmedapps.watchy.favorites.data.remote.request.MediaRequest
import com.ahmedapps.watchy.favorites.data.remote.responde.BackendMediaDto

interface BackendFavoriteMediaRepository {

    suspend fun getLikedMediaItemList(): List<BackendMediaDto>?
    suspend fun getBookmarkedMediaItemList(): List<BackendMediaDto>?

    suspend fun getMediaById(
        mediaId: Int
    ): BackendMediaDto?

    suspend fun upsertMediaToUser(
        mediaRequest: MediaRequest
    ): Boolean

    suspend fun deleteMediaFromUser(
        mediaId: Int
    ): Boolean


}















