package com.ahmedapps.watchy.search.presentation

import com.ahmedapps.watchy.main.domain.models.Media

sealed class SearchUiEvents {
    data class OnSearchQueryChanged(val query: String) : SearchUiEvents()
    data class OnSearchedItemClick(val media: Media) : SearchUiEvents()
    object OnPaginate : SearchUiEvents()
}