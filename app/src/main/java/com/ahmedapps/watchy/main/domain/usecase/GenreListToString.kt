package com.ahmedapps.watchy.main.domain.usecase

import com.ahmedapps.watchy.util.Constants.genres
 fun genreListToString(
    genresNames: List<String>
): String {


    return genresNames.mapNotNull { name ->
        genres.find { genre ->
            genre.name == name
        }?.name
    }.joinToString(" - ")
}













