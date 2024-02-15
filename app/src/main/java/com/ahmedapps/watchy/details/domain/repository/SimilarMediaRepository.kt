package com.ahmedapps.watchy.details.domain.repository

import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow

interface SimilarMediaRepository {

    suspend fun getSimilarMediaList(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

}










