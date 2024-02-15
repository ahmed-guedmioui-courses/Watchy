package com.ahmedapps.watchy.main.data.local.genres

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [com.ahmedapps.watchy.main.data.local.genres.GenreEntity::class],
    version = 1
)
abstract class GenresDatabase: RoomDatabase() {
    abstract val genreDao: com.ahmedapps.watchy.main.data.local.genres.GenreDao
}