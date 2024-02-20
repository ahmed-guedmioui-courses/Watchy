package com.ahmedapps.watchy.main.domain.usecase

import com.ahmedapps.watchy.util.Constants.genres

/**
 * @author Ahmed Guedmioui
 */
fun provideGenres(genreIds: List<Int>): List<String> {
    return genres.filter { genre ->
        genre.id in genreIds
    }.map { genre -> genre.name }
}








