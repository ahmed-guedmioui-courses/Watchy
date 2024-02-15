package com.ahmedapps.watchy.favorites.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FavoriteMediaDao {

    @Query("SELECT EXISTS(SELECT 1 FROM favoriteMediaEntity WHERE mediaId = :id)")
    suspend fun doesFavoriteMediaItemAlreadyExist(id: Int): Boolean

    @Upsert
    suspend fun upsertFavoriteMediaItem(favoriteMediaEntity: FavoriteMediaEntity)

    @Upsert
    suspend fun upsertFavoriteMediaList(favoriteMediaEntityList: List<FavoriteMediaEntity>)

    @Delete
    suspend fun deleteFavoriteMediaItemById(favoriteMediaEntity: FavoriteMediaEntity)

    @Query("SELECT * FROM favoriteMediaEntity WHERE mediaId = :id")
    suspend fun getFavoriteMediaItemById(id: Int): FavoriteMediaEntity?

    @Query("SELECT * FROM favoriteMediaEntity WHERE isLiked = 1")
    suspend fun getLikedMediaItemList(): List<FavoriteMediaEntity>

    @Query("SELECT * FROM favoriteMediaEntity WHERE isBookmarked = 1")
    suspend fun getBookmarkedItemList(): List<FavoriteMediaEntity>

    @Query("SELECT * FROM favoriteMediaEntity")
    suspend fun getAllFavoriteMediaItem(): List<FavoriteMediaEntity>

    @Query("DELETE FROM favoriteMediaEntity")
    suspend fun deleteAllFavoriteMediaItems()

}