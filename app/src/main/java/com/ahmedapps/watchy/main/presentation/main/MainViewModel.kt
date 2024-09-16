package com.ahmedapps.watchy.main.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.watchy.main.data.remote.api.MediaApi.Companion.API_KEY
import com.ahmedapps.watchy.main.domain.repository.MainRepository
import com.ahmedapps.watchy.profile.domain.repository.ProfileRepository
import com.ahmedapps.watchy.util.APIConstants.ALL
import com.ahmedapps.watchy.util.APIConstants.MOVIE
import com.ahmedapps.watchy.util.APIConstants.POPULAR
import com.ahmedapps.watchy.util.APIConstants.TRENDING_TIME
import com.ahmedapps.watchy.util.APIConstants.TV
import com.ahmedapps.watchy.util.Resource
import com.ahmedapps.watchy.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

    fun onEvent(event: MainUiEvents) {
        when (event) {

            is MainUiEvents.Refresh -> {

                _mainUiState.update {
                    it.copy(isLoading = true)
                }

                when (event.route) {

                    Route.MAIN_SCREEN -> {
                        _mainUiState.update { it.copy(specialList = emptyList()) }

                        loadPopularMovies(
                            fetchFromRemote = true, isRefresh = true
                        )
                        loadPopularTvSeries(
                            fetchFromRemote = true, isRefresh = true
                        )
                        loadTrendingAll(
                            fetchFromRemote = true, isRefresh = true
                        )

                    }

                    Route.TRENDING_NOW_SCREEN -> {
                        loadTrendingAll(
                            fetchFromRemote = true, isRefresh = true
                        )
                    }

                    Route.MOVIES_SCREEN -> {
                        loadPopularMovies(
                            fetchFromRemote = true, isRefresh = true
                        )
                    }

                    Route.TV_SERIES_SCREEN -> {
                        loadPopularTvSeries(
                            fetchFromRemote = true, isRefresh = true
                        )
                    }

                }
            }

            is MainUiEvents.OnPaginate -> {

                when (event.route) {

                    Route.TRENDING_NOW_SCREEN -> {
                        loadTrendingAll(fetchFromRemote = true)
                    }

                    Route.MOVIES_SCREEN -> {
                        loadPopularMovies(fetchFromRemote = true)
                    }

                    Route.TV_SERIES_SCREEN -> {
                        loadPopularTvSeries(fetchFromRemote = true)
                    }

                }
            }

            MainUiEvents.LoadAll -> {
                loadAll()
            }
        }
    }

    private fun loadAll(fetchFromRemote: Boolean = false) {
        loadPopularMovies(fetchFromRemote)
        loadPopularTvSeries(fetchFromRemote)
        loadTrendingAll(fetchFromRemote)

        viewModelScope.launch {
            _mainUiState.update {
                it.copy(name = profileRepository.getName())
            }
        }
    }

    private fun loadTrendingAll(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {

        viewModelScope.launch {

            mainRepository
                .getTrendingList(
                    fetchFromRemote,
                    isRefresh,
                    ALL,
                    TRENDING_TIME,
                    mainUiState.value.trendingAllPage,
                    API_KEY
                )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { mediaList ->

                                val shuffledMediaList = mediaList.toMutableList()
                                shuffledMediaList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            trendingAllList = shuffledMediaList.toList(),
                                            trendingAllPage = 1,
                                            specialList = it.specialList +
                                                    shuffledMediaList.toList().take(2)
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            trendingAllList =
                                            it.trendingAllList + shuffledMediaList.toList(),
                                            trendingAllPage = it.trendingAllPage + 1
                                        )
                                    }

                                    if (!fetchFromRemote) {
                                        _mainUiState.update {
                                            it.copy(
                                                specialList = it.specialList +
                                                        shuffledMediaList.toList().take(2)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }
        }
    }


    private fun loadPopularTvSeries(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {

        viewModelScope.launch {


            mainRepository
                .getMoviesAndTvSeriesList(
                    fetchFromRemote,
                    isRefresh,
                    TV,
                    POPULAR,
                    mainUiState.value.popularTvSeriesPage,
                    API_KEY
                )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { mediaList ->

                                val shuffledMediaList = mediaList.toMutableList()
                                shuffledMediaList.shuffle()


                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            popularTvSeriesList = shuffledMediaList.toList(),
                                            popularTvSeriesPage = 1,
                                            specialList = it.specialList +
                                                    shuffledMediaList.toList().take(2)
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            popularTvSeriesList =
                                            it.popularTvSeriesList + shuffledMediaList.toList(),
                                            popularTvSeriesPage = it.popularTvSeriesPage + 1
                                        )
                                    }

                                    if (!fetchFromRemote) {
                                        _mainUiState.update {
                                            it.copy(
                                                specialList = it.specialList +
                                                        shuffledMediaList.toList().take(2)
                                            )
                                        }
                                    }
                                }

                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun loadPopularMovies(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {

        viewModelScope.launch {

            mainRepository
                .getMoviesAndTvSeriesList(
                    fetchFromRemote,
                    isRefresh,
                    MOVIE,
                    POPULAR,
                    mainUiState.value.popularMoviesPage,
                    API_KEY
                )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { mediaList ->

                                val shuffledMediaList = mediaList.toMutableList()
                                shuffledMediaList.shuffle()

                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            popularMoviesList = shuffledMediaList.toList(),
                                            popularMoviesPage = 1,
                                            specialList = it.specialList +
                                                    shuffledMediaList.toList().take(2)
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            popularMoviesList = it.popularMoviesList + shuffledMediaList.toList(),
                                            popularMoviesPage = it.popularMoviesPage + 1
                                        )
                                    }

                                    if (!fetchFromRemote) {
                                        _mainUiState.update {
                                            it.copy(
                                                specialList = it.specialList +
                                                        shuffledMediaList.toList().take(2)
                                            )
                                        }
                                    }
                                }

                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }
        }
    }

}






