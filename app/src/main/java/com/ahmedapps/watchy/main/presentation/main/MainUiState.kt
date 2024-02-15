package com.ahmedapps.watchy.main.presentation.main

import com.ahmedapps.watchy.main.domain.models.Genre
import com.ahmedapps.watchy.main.domain.models.Media

data class MainUiState(

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val areListsToBuildSpecialListEmpty: Boolean = true,

    val popularMoviesPage: Int = 1,
    val popularTvSeriesPage: Int = 1,
    val trendingAllPage: Int = 1,

    val trendingAllList: List<Media> = emptyList(),
    val popularMoviesList: List<Media> = emptyList(),
    val popularTvSeriesList: List<Media> = emptyList(),

    // trendingAllList + popularMoviesList + popularTvSeriesList (Two items in each list)
    val specialList: List<Media> = emptyList(),

    val moviesGenresList: List<Genre> = emptyList(),
    val tvGenresList: List<Genre> = emptyList(),

    val name: String = ""

)