package com.ahmedapps.watchy.main.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
    val mediaId: Int,

    val isLiked: Boolean,
    val isBookmarked: Boolean,

    val adult: Boolean,
    val backdropPath: String,
    val genres: List<String>,
    val mediaType: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    var category: String,

    var runtime: Int,
    var status: String,
    var tagline: String,

    val videosIds: List<String>,
    var similarMediaList: List<Int>,
) : Parcelable