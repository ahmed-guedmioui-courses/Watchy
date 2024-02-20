package com.ahmedapps.watchy.main.domain.usecase

import com.ahmedapps.watchy.util.APIConstants.MOVIE
import com.ahmedapps.watchy.util.Constants.movieGenres
import com.ahmedapps.watchy.util.Constants.tvSeriesGenres

fun genreListToString(
    genres: List<String>,
    type: String
): String {

    val genresList = if (type == MOVIE) movieGenres else tvSeriesGenres

    return genres.mapNotNull { name ->
        genresList.find { genre ->
            genre.name == name
        }?.name
    }.joinToString(" - ")
}













