package com.ahmedapps.watchy.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmedapps.watchy.profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favoritesScreenState = MutableStateFlow(FavoritesScreenState())
    val favoritesScreenState = _favoritesScreenState.asStateFlow()

    init {
       load()

        viewModelScope.launch {
            favoritesRepository.favoritesDbUpdateEventFlow().collect { isUpdated ->
                if (isUpdated) {
                    loadLikedMediaList()
                    loadBookmarkedMediaList()
                }
            }
        }

        viewModelScope.launch {
            _favoritesScreenState.update {
                it.copy(name = profileRepository.getName())
            }
        }
    }

    fun onEvent(event: FavoriteUiEvents) {
        when (event) {
            is FavoriteUiEvents.SetDataAndLoad -> {
                _favoritesScreenState.update {
                    it.copy(
                        moviesGenresList = event.moviesGenresList,
                        tvGenresList = event.tvGenresList
                    )
                }
            }

            FavoriteUiEvents.Refresh -> {
                viewModelScope.launch {
                    favoritesRepository.syncFavoriteMediaItems()
                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            favoritesRepository.syncFavoriteMediaItems()
            loadLikedMediaList()
            loadBookmarkedMediaList()
        }

    }

    private fun loadLikedMediaList() {
        viewModelScope.launch {
            _favoritesScreenState.update {
                it.copy(
                    likedList = favoritesRepository.getLikedMediaItemList()
                )
            }
        }
    }

    private fun loadBookmarkedMediaList() {
        viewModelScope.launch {
            _favoritesScreenState.update {
                it.copy(
                    watchlist = favoritesRepository.getBookmarkedMediaItemList()
                )
            }
        }
    }

}






