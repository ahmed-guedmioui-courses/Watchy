package com.ahmedapps.watchy.main.data.local.media

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MediaDao {

    @Upsert
    suspend fun upsertMediaList(mediaEntities: List<MediaEntity>)

    @Upsert
    suspend fun upsertMediaItem(mediaItem: MediaEntity)

    @Query(
        "DELETE FROM mediaEntity WHERE mediaType = :mediaType AND category = :category"
    )
    suspend fun deleteMediaByTypeAndCategory(mediaType: String, category: String)

    @Query("SELECT * FROM mediaEntity WHERE mediaId = :id")
    suspend fun getMediaById(id: Int): MediaEntity

    @Query("SELECT COUNT(*) FROM mediaEntity WHERE mediaId = :id")
    suspend fun doesMediaExist(id: Int): Int

    @Query("SELECT * FROM mediaEntity")
    suspend fun getAllMedia(): List<MediaEntity>

    @Query(
        "SELECT * FROM mediaEntity WHERE mediaType = :mediaType AND category = :category"
    )
    suspend fun getMediaListByTypeAndCategory(
        mediaType: String, category: String
    ): List<MediaEntity>

    @Query("DELETE FROM mediaEntity WHERE category = :category")
    suspend fun deleteTrendingMediaList(category: String)


    @Query("SELECT * FROM mediaEntity WHERE category = :category")
    suspend fun getMediaListByCategory(category: String): List<MediaEntity>

    @Query("DELETE FROM mediaEntity")
    suspend fun deleteAllMediaItems()
}