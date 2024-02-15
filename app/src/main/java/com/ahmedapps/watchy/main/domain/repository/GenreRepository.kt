package com.ahmedapps.watchy.main.domain.repository

import com.ahmedapps.watchy.util.Resource
import com.ahmedapps.watchy.main.domain.models.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun getGenres(
        fetchFromRemote: Boolean,
        type: String,
        apiKey: String
    ): Flow<Resource<List<Genre>>>

    suspend fun clearGenresDb()
}










