package com.ahmedapps.watchy.favorites.data.mapper

import com.ahmedapps.watchy.favorites.data.local.FavoriteMediaEntity
import com.ahmedapps.watchy.favorites.data.remote.request.MediaRequest
import com.ahmedapps.watchy.favorites.data.remote.responde.BackendMediaDto
import com.ahmedapps.watchy.main.domain.models.Media

fun FavoriteMediaEntity.toMedia(): Media {
    return Media(
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
        genres = try {
            genres.split(",").map { it }
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

        videosIds = try {
            videosIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        similarMediaList = try {
            similarMediaList.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    )
}

fun FavoriteMediaEntity.toMediaRequest(): MediaRequest {
    return MediaRequest(
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
        genres = genres,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status,
        runtime = runtime,
        tagline = tagline,

        videosIds = videosIds,
        similarMediaList = similarMediaList
    )
}

fun BackendMediaDto.toFavoriteMediaEntity(): FavoriteMediaEntity {
    return FavoriteMediaEntity(
        mediaId = mediaId,

        isSynced = true,
        isDeletedLocally = false,

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
        genres = genres,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status,
        runtime = runtime,
        tagline = tagline,

        videosIds = videosIds,
        similarMediaList = similarMediaList
    )
}

fun Media.toFavoriteMediaEntity(): FavoriteMediaEntity {
    return FavoriteMediaEntity(
        mediaId = mediaId,

        isSynced = false,
        isDeletedLocally = false,

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
        genres = try {
            genres.joinToString(",")
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


fun BackendMediaDto.toMedia(): Media {
    return Media(
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
        genres = try {
            genres.split(",").map { it }
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

        videosIds = try {
            videosIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        similarMediaList = try {
            similarMediaList.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    )
}


fun Media.toMediaRequest(): MediaRequest {
    return MediaRequest(
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
        genres = try {
            genres.joinToString(",")
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










