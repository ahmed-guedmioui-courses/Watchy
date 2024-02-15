package com.ahmedapps.watchy.favorites.presentation

import com.ahmedapps.watchy.main.domain.models.Genre

sealed class FavoriteUiEvents {

    data class SetDataAndLoad(
        val moviesGenresList: List<Genre>,
        val tvGenresList: List<Genre>,
    ) : FavoriteUiEvents()

    object Refresh: FavoriteUiEvents()
}