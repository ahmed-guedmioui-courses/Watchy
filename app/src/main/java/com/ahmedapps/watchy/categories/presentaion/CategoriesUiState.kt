package com.ahmedapps.watchy.categories.presentaion

import com.ahmedapps.watchy.main.domain.models.Genre
import com.ahmedapps.watchy.main.domain.models.Media

data class CategoriesUiState(

    val actionAndAdventureList: List<Media> = emptyList(),
    val dramaList: List<Media> = emptyList(),
    val comedyList: List<Media> = emptyList(),
    val sciFiAndFantasyList: List<Media> = emptyList(),
    val animationList: List<Media> = emptyList(),

    val moviesGenresList: List<Genre> = emptyList(),
    val tvGenresList: List<Genre> = emptyList()

)