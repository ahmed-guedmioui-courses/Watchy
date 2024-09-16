package com.ahmedapps.watchy.categories.data.repository

import com.ahmedapps.watchy.categories.domain.repository.CategoriesRepository
import com.ahmedapps.watchy.main.data.local.media.MediaDatabase
import com.ahmedapps.watchy.main.data.mapper.toMedia
import com.ahmedapps.watchy.main.domain.models.Genre
import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.util.Constants.genres
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class CategoriesRepositoryImpl @Inject constructor(
    mediaDb: MediaDatabase
) : CategoriesRepository {

    private val mediaDao = mediaDb.mediaDao

    override suspend fun getActionAndAdventureCategory(): List<Media> {
        val actionAndAdventureGenres = genres.filter {
            it.name in listOf(
                "Adventure", "Horror", "Action", "Western", "Thriller", "Crime", "War"
            )
        }.map { it.name }
        return getMediaByGenreIds(actionAndAdventureGenres)
    }

    override suspend fun getDramaCategory(): List<Media> {
        val dramaGenres = genres.filter {
            it.name in listOf("Drama", "Comedy", "Family", "Romance", "Music", "TV Movie")
        }.map { it.name }
        return getMediaByGenreIds(dramaGenres)
    }

    override suspend fun getComedyCategory(): List<Media> {
        val comedyGenres = genres.filter {
            it.name in listOf("Comedy", "Family", "Romance")
        }.map { it.name }
        return getMediaByGenreIds(comedyGenres)
    }

    override suspend fun getSciFiFantasyCategory(): List<Media> {
        val sciFiFantasyGenres = genres.filter {
            it.name in listOf(
                "Fantasy",
                "Horror",
                "Thriller",
                "Crime",
                "Documentary",
                "Science Fiction",
                "Mystery",
                "TV Movie"
            )
        }.map { it.name }
        return getMediaByGenreIds(sciFiFantasyGenres)
    }

    override suspend fun getAnimationCategory(): List<Media> {
        val animationGenres = genres.filter {
            it.name == "Animation"
        }.map { it.name }
        return getMediaByGenreIds(animationGenres)
    }


    private suspend fun getMediaByGenreIds(genres: List<String>): List<Media> {
        val mediaList = mediaDao.getAllMedia().map { it.toMedia() }

        return mediaList.filter { media ->
            media.genres.any { genre ->
                genre in genres
            }
        }.shuffled()
    }
}






