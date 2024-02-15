package com.ahmedapps.watchy.details.presentation.similar

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
import com.ahmedapps.watchy.details.presentation.detail_ui_components.SimilarItem
import com.ahmedapps.watchy.details.presentation.details.DetailsScreenState
import com.ahmedapps.watchy.ui.theme.HugeRadius
import com.ahmedapps.watchy.ui.ui_shared_components.ListShimmerEffect
import com.ahmedapps.watchy.ui.ui_shared_components.NonFocusedTopBar
import kotlin.math.roundToInt

@Composable
fun SimilarScreen(
    mainNavController: NavController,
    detailsScreenState: DetailsScreenState,
    name: String,
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

    val title = stringResource(R.string.similar_to, name)

    val mediaList = detailsScreenState.similarList

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        if (mediaList.isEmpty()) {
            ListShimmerEffect(HugeRadius)
        } else {

            val listState = rememberLazyGridState()

            LazyVerticalGrid(
                state = listState,
                contentPadding = PaddingValues(top = HugeRadius.dp),
                columns = GridCells.Adaptive(190.dp),
            ) {

                items(mediaList.size) { i ->
                    SimilarItem(
                        media = mediaList[i],
                        mainNavController = mainNavController,
                        detailsScreenState = detailsScreenState
                    )
                }

            }
        }

        NonFocusedTopBar(
            title = title,
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
            mainNavController = mainNavController,
        )
    }
}
