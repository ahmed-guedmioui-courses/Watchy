package com.ahmedapps.watchy.favorites.data.remote

import com.ahmedapps.watchy.favorites.data.remote.request.MediaByIdRequest
import com.ahmedapps.watchy.favorites.data.remote.request.UpsertMediaRequest
import com.ahmedapps.watchy.favorites.data.remote.responde.BackendMediaDto
import com.ahmedapps.watchy.util.BackendConstants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendMediaApi {

    @GET(BackendConstants.GET_LIKED_MEDIA_LIST)
    suspend fun getLikedMediaItemList(
        @Path("email") email: String
    ): List<BackendMediaDto>

    @GET(BackendConstants.GET_BOOKMARKED_MEDIA_LIST)
    suspend fun getBookmarkedMediaItemList(
        @Path("email") email: String
    ): List<BackendMediaDto>

    @GET(BackendConstants.GET_MEDIA_BY_ID)
    suspend fun getMediaById(
        @Path("mediaId") mediaId: String,
        @Path("email") email: String,
    ): BackendMediaDto

    @POST(BackendConstants.UPSERT_MEDIA_TO_USER)
    suspend fun upsertMediaToUser(
        @Body request: UpsertMediaRequest
    ): String

    @POST(BackendConstants.DELETE_MEDIA_FROM_USER)
    suspend fun deleteMediaFromUser(
        @Body request: MediaByIdRequest
    ): String
}














