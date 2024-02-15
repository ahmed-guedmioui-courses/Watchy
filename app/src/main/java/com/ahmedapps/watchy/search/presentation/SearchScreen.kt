package com.ahmedapps.watchy.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedapps.watchy.main.presentation.main.MainUiState
import com.ahmedapps.watchy.search.presentation.searchScreenUiComponents.SearchMediaItem
import com.ahmedapps.watchy.ui.theme.BigRadius
import com.ahmedapps.watchy.ui.ui_shared_components.FocusedTopBar
import kotlin.math.roundToInt

@Composable
fun SearchScreen(
    mainNavController: NavController,
    mainUiState: MainUiState,
) {

    val searchViewModel = hiltViewModel<SearchViewModel>()
    val searchScreenState = searchViewModel.searchScreenState.collectAsState().value

    val toolbarHeightPx = with(LocalDensity.current) { BigRadius.dp.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        LazyVerticalGrid(
            contentPadding = PaddingValues(top = BigRadius.dp),
            columns = GridCells.Fixed(2),
        ) {

            items(searchScreenState.searchList.size) { index ->
                SearchMediaItem(
                    media = searchScreenState.searchList[index],
                    mainNavController = mainNavController,
                    mainUiState = mainUiState,
                    onEvent = searchViewModel::onEvent
                )

                if (index >= searchScreenState.searchList.size - 1 && !searchScreenState.isLoading
                ) {
                    LaunchedEffect(key1 = true) {
                        searchViewModel.onEvent(SearchUiEvents.OnPaginate)
                    }
                }
            }
        }

        FocusedTopBar(
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
            searchScreenState = searchScreenState
        ) {
            searchViewModel.onEvent(SearchUiEvents.OnSearchQueryChanged(it))
        }

    }
}

