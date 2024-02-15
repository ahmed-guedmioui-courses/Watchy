package com.ahmedapps.watchy.details.domain.repository

import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow

interface MediaDetailsRepository {

    suspend fun getDetails(
        type: String,
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Media>>

    suspend fun getVideosList(
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<List<String>>>

}










