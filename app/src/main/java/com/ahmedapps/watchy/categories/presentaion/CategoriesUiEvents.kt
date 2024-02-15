package com.ahmedapps.watchy.categories.presentaion

import com.ahmedapps.watchy.main.domain.models.Genre

sealed class CategoriesUiEvents {

    data class SetDataAndLoad(
        val moviesGenresList: List<Genre>,
        val tvGenresList: List<Genre>,
    ) : CategoriesUiEvents()

}