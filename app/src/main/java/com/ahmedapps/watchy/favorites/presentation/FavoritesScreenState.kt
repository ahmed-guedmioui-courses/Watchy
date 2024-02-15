package com.ahmedapps.watchy.favorites.presentation

import com.ahmedapps.watchy.main.domain.models.Genre
import com.ahmedapps.watchy.main.domain.models.Media

data class FavoritesScreenState(

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,

    val likedList: List<Media> = emptyList(),
    val watchlist: List<Media> = emptyList(),

    val moviesGenresList: List<Genre> = emptyList(),
    val tvGenresList: List<Genre> = emptyList(),

    val name: String = ""

)