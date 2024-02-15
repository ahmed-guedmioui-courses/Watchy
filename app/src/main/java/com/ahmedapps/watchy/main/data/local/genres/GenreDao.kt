package com.ahmedapps.watchy.main.data.local.genres

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface GenreDao {

    @Upsert
    suspend fun upsertGenres(
        mediaEntities: List<GenreEntity>
    )

    @Query("SELECT * FROM genreEntity WHERE type = :mediaType")
    suspend fun getGenres(mediaType: String): List<GenreEntity>

    @Query("DELETE FROM genreEntity")
    suspend fun deleteAllGenresItems()

}