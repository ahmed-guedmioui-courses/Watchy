package com.ahmedapps.watchy.details.presentation.details

import com.ahmedapps.watchy.main.domain.models.Genre

sealed interface DetailsUiEvents {

    data class SetDataAndLoad(
        val moviesGenresList: List<Genre>,
        val tvGenresList: List<Genre>,
        val id: Int
    ) : DetailsUiEvents

    data class ShowAndHideAlertDialog(
        val alertDialogType: Int = 0
    ) : DetailsUiEvents

    object Refresh : DetailsUiEvents

    object NavigateToWatchVideo : DetailsUiEvents

    object LikeOrDislike : DetailsUiEvents

    object AddOrRemoveFromWatchlist : DetailsUiEvents
}