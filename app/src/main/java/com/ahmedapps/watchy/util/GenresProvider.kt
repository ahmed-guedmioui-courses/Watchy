package com.ahmedapps.watchy.util

import com.ahmedapps.watchy.main.domain.models.Genre
import timber.log.Timber

fun genresProvider(
    genreIds: List<Int>,
    allGenres: List<Genre>
): String {

    var genres = ""

    for (i in allGenres.indices) {
        Timber.tag("genres").d("${allGenres[i].name}, ${allGenres[i].id}")
        for (j in genreIds.indices) {
            if (allGenres[i].id == genreIds[j]) {
                genres += "${allGenres[i].name} - "
            }
        }
    }

    return genres.dropLastWhile { it == ' ' || it == '-' }
}













