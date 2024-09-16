package com.ahmedapps.watchy.favorites.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMediaEntity(
    @PrimaryKey val mediaId: Int,

    val isSynced: Boolean,
    val isDeletedLocally: Boolean,

    val isLiked: Boolean,
    val isBookmarked: Boolean,

    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genres: String,
    var mediaType: String,
    val originCountry: String,
    val originalLanguage: String,
    val originalName: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var category: String,

    var runtime: Int,
    var status: String,
    var tagline: String,

    var videosIds: String,
    var similarMediaList: String
)
