package com.ahmedapps.watchy.categories.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.watchy.categories.domain.repository.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val _categoriesUiState = MutableStateFlow(CategoriesUiState())
    val categoriesUiState = _categoriesUiState.asStateFlow()

    init {
        loadLists()
    }

    fun onEvent(event: CategoriesUiEvents) {
        when (event) {
            is CategoriesUiEvents.SetDataAndLoad -> {
                _categoriesUiState.update {
                    it.copy(
                        moviesGenresList = event.moviesGenresList,
                        tvGenresList = event.tvGenresList
                    )
                }
            }

        }
    }

    private fun loadLists() {
        viewModelScope.launch {
            _categoriesUiState.update {
                it.copy(
                    actionAndAdventureList = categoriesRepository.getActionAndAdventureCategory(),
                    dramaList = categoriesRepository.getDramaCategory(),
                    comedyList = categoriesRepository.getComedyCategory(),
                    sciFiAndFantasyList = categoriesRepository.getSciFiFantasyCategory(),
                    animationList = categoriesRepository.getAnimationCategory()
                )
            }
        }
    }
}






