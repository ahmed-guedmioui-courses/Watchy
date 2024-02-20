package com.ahmedapps.watchy.main.domain.usecase

import com.ahmedapps.watchy.util.APIConstants.MOVIE
import com.ahmedapps.watchy.util.Constants.movieGenres
import com.ahmedapps.watchy.util.Constants.tvSeriesGenres

/**
 * @author Ahmed Guedmioui
 */
fun provideGenres(type: String, genreIds: List<Int>): List<String> {
    val genresList = if (type == MOVIE) movieGenres else tvSeriesGenres

    return genresList.filter { genre ->
        genre.id in genreIds
    }.map { genre -> genre.name }
}








