package com.ahmedapps.watchy.search.domain.repository

import com.ahmedapps.watchy.util.Resource
import com.ahmedapps.watchy.main.domain.models.Media
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchList(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

}










