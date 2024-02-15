package com.ahmedapps.watchy.main.domain.repository

import com.ahmedapps.watchy.util.Resource
import com.ahmedapps.watchy.main.domain.models.Media
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun upsertMediaList(mediaList: List<Media>)

    suspend fun upsertMediaItem(media: Media)

    suspend fun insertSearchedMedia(media: Media)

    suspend fun getMediaById(id: Int): Media

    suspend fun getMediaListByCategory(category: String): List<Media>

    suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>

    suspend fun clearMediaDb()

}










