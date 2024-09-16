package com.ahmedapps.watchy.categories.presentaion.category_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.categories.presentaion.CategoriesUiState
import com.ahmedapps.watchy.ui.theme.HugeRadius
import com.ahmedapps.watchy.ui.ui_shared_components.NonFocusedTopBar
import com.ahmedapps.watchy.util.Route
import kotlin.math.roundToInt

@Composable
fun CategoryListScreen(
    route: String,
    mainNavController: NavController,
    categoriesUiState: CategoriesUiState
) {

    val toolbarHeightPx = with(LocalDensity.current) { HugeRadius.dp.roundToPx().toFloat() }
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

    val mediaList = when (route) {
        Route.ACTION_AND_ADVENTURE_CATEGORY_SCREEN -> categoriesUiState.actionAndAdventureList
        Route.DRAMA_CATEGORY_SCREEN -> categoriesUiState.dramaList
        Route.COMEDY_CATEGORY_SCREEN -> categoriesUiState.comedyList
        Route.SCI_FI_AND_FANTASY_CATEGORY_SCREEN -> categoriesUiState.sciFiAndFantasyList
        else -> categoriesUiState.animationList
    }

    val title = when (route) {
        Route.ACTION_AND_ADVENTURE_CATEGORY_SCREEN -> stringResource(R.string.action_and_adventure)
        Route.DRAMA_CATEGORY_SCREEN -> stringResource(R.string.drama)
        Route.COMEDY_CATEGORY_SCREEN -> stringResource(R.string.comedy)
        Route.SCI_FI_AND_FANTASY_CATEGORY_SCREEN -> stringResource(R.string.sci_fi_and_fantasy)
        else -> stringResource(R.string.animation)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        val listState = rememberLazyGridState()

        LazyVerticalGrid(
            state = listState,
            contentPadding = PaddingValues(top = HugeRadius.dp),
            columns = GridCells.Adaptive(190.dp),
        ) {

            items(mediaList.size) { i ->
                CategoryMediaItem(
                    media = mediaList[i],
                    mainNavController = mainNavController
                )
            }

        }

        NonFocusedTopBar(
            title = title,
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
            mainNavController = mainNavController,
        )

    }
}