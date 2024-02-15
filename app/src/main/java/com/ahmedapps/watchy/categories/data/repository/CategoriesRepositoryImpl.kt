package com.ahmedapps.watchy.categories.data.repository

import com.ahmedapps.watchy.categories.domain.repository.CategoriesRepository
import com.ahmedapps.watchy.main.data.local.media.MediaDatabase
import com.ahmedapps.watchy.main.data.mapper.toMedia
import com.ahmedapps.watchy.main.domain.models.Media
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class CategoriesRepositoryImpl @Inject constructor(
    mediaDb: MediaDatabase
) : CategoriesRepository {

    private val mediaDao = mediaDb.mediaDao

    override suspend fun getActionAndAdventureCategory(): List<Media> =
        getMediaByGenreIds(listOf(28, 12, 27, 37, 53, 80, 10752))

    override suspend fun getDramaCategory(): List<Media> =
        getMediaByGenreIds(listOf(18, 35, 36, 9648, 10402, 10749))

    override suspend fun getComedyCategory(): List<Media> =
        getMediaByGenreIds(listOf(35, 10751, 10749, 10402))

    override suspend fun getSciFiFantasyCategory(): List<Media> =
        getMediaByGenreIds(listOf(14, 27, 53, 80, 99, 878, 9648, 10770))

    override suspend fun getAnimationCategory(): List<Media> =
        getMediaByGenreIds(listOf(16))

    private suspend fun getMediaByGenreIds(genreIds: List<Int>): List<Media> {
        val mediaList = mediaDao.getAllMedia().map { it.toMedia() }
        val filteredList = mutableListOf<Media>()

        for (media in mediaList) {
            if (media.genreIds.any { it in genreIds }) {
                filteredList.add(media)
            }
        }

        return filteredList.shuffled()
    }
}


/*
    Adventure, 12
    Fantasy, 14
    Animation, 16
    Drama, 18
    Horror, 27
    Action, 28
    Comedy, 35
    History, 36
    Western, 37
    Thriller, 53
    Crime, 80
    Documentary, 99
    Science Fiction, 878
    Mystery, 9648
    Music, 10402
    Romance, 10749
    Family, 10751
    War, 10752
    TV Movie, 10770

*/







