package com.ahmedapps.watchy.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.watchy.main.data.remote.api.MediaApi.Companion.API_KEY
import com.ahmedapps.watchy.main.domain.repository.MainRepository
import com.ahmedapps.watchy.search.domain.repository.SearchRepository
import com.ahmedapps.watchy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState = _searchScreenState.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchUiEvents) {
        when (event) {

            is SearchUiEvents.OnSearchedItemClick -> {
                viewModelScope.launch {
                    mainRepository.insertSearchedMedia(event.media)
                }
            }

            is SearchUiEvents.OnSearchQueryChanged -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)

                    _searchScreenState.update {
                        it.copy(
                            searchQuery = event.query,
                            searchList = emptyList()
                        )
                    }

                    loadSearchList()
                }
            }

            is SearchUiEvents.OnPaginate -> {
                _searchScreenState.update {
                    it.copy(
                        searchPage = it.searchPage + 1
                    )
                }
                loadSearchList()
            }

        }
    }

    private fun loadSearchList() {

        viewModelScope.launch {

            searchRepository
                .getSearchList(
                    fetchFromRemote = true,
                    query = searchScreenState.value.searchQuery,
                    page = searchScreenState.value.searchPage,
                    apiKey = API_KEY
                )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { mediaList ->
                                _searchScreenState.update {
                                    it.copy(
                                        searchList = it.searchList + mediaList
                                    )
                                }
                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            _searchScreenState.update {
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






