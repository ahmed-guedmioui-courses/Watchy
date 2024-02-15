package com.ahmedapps.watchy.favorites.domain.repository

import com.ahmedapps.watchy.main.domain.models.Media
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun favoritesDbUpdateEventFlow(): Flow<Boolean>

    suspend fun upsertFavoriteMediaItem(media: Media)

    suspend fun deleteFavoriteMediaItem(media: Media)

    suspend fun getFavoriteMediaItemById(id: Int): Media?

    suspend fun getLikedMediaItemList(): List<Media>

    suspend fun getBookmarkedMediaItemList(): List<Media>

    suspend fun syncFavoriteMediaItems()
    suspend fun clearFavoritesDb()

}










