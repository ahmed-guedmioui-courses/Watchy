package com.ahmedapps.watchy.main.data.mapper

import com.ahmedapps.watchy.main.data.local.media.MediaEntity
import com.ahmedapps.watchy.main.data.remote.dto.MediaDto
import com.ahmedapps.watchy.main.domain.models.Media

fun MediaEntity.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage ?: "",
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        originalTitle = originalTitle,
        category = category,

        runtime = runtime,
        status = status,
        tagline = tagline,

        videosIds = if (videosIds.isEmpty()) {
            emptyList()
        } else {
            try {
                videosIds.split(",").map { it }
            } catch (e: Exception) {
                emptyList()
            }
        },

        similarMediaList = if (similarMediaList.isEmpty()) {
            emptyList()
        } else {
            try {
                similarMediaList.split(",").map { it.toInt() }
            } catch (e: Exception) {
                emptyList()
            }
        }
    )
}

fun MediaDto.toMediaEntity(
    type: String,
    category: String,
    isFavorite: Boolean,
    isInWatchlist: Boolean
): MediaEntity {
    return MediaEntity(
        mediaId = id ?: 0,

        isLiked = isFavorite,
        isBookmarked = isInWatchlist,

        backdropPath = backdrop_path ?: "",
        originalLanguage = original_language ?: "",
        overview = overview ?: "",
        posterPath = poster_path ?: "",
        releaseDate = release_date ?: first_air_date ?: "",
        title = title ?: name ?: "",
        originalName = original_name ?: "",
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = try {
            genre_ids?.joinToString(",") ?: ""
        } catch (e: Exception) {
            ""
        },
        adult = adult ?: false,
        mediaType = type,
        category = category,
        originCountry = try {
            origin_country?.joinToString(",") ?: ""
        } catch (e: Exception) {
            ""
        },
        originalTitle = original_title ?: original_name ?: "",
        firstAirDate = first_air_date ?: "",
        video = video ?: false,

        status = "",
        runtime = 0,
        tagline = "",

        videosIds = "",
        similarMediaList = ""
    )
}

fun Media.toMediaEntity(): MediaEntity {
    return MediaEntity(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        originalTitle = originalTitle,
        category = category,
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status,
        runtime = runtime,
        tagline = tagline,

        videosIds = try {
            videosIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        similarMediaList = try {
            similarMediaList.joinToString(",")
        } catch (e: Exception) {
            ""
        }
    )
}


// for getting search result
fun MediaDto.toMedia(
    type: String,
    category: String,
    isFavorite: Boolean,
    isInWatchlist: Boolean
): Media {
    return Media(
        mediaId = id ?: 0,

        isLiked = isFavorite,
        isBookmarked = isInWatchlist,

        backdropPath = backdrop_path ?: "",
        originalLanguage = original_language ?: "",
        overview = overview ?: "",
        posterPath = poster_path ?: "",
        releaseDate = release_date ?: first_air_date ?: "",
        title = title ?: name ?: "",
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = genre_ids ?: emptyList(),
        adult = adult ?: false,
        mediaType = type,
        category = category,
        originCountry = origin_country ?: emptyList(),
        originalTitle = original_title ?: original_name ?: "",

        runtime = 0,
        status = "",
        tagline = "",

        videosIds = emptyList(),
        similarMediaList = emptyList()
    )
}






