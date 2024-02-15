package com.ahmedapps.watchy.main.presentation.main.ui_componentes

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmedapps.watchy.main.presentation.main.MainUiState

@Composable
fun MediaHomeScreenSectionOrShimmer(
    route: String,
    showShimmer: Boolean,
    mainNavController: NavController,
    mainUiState: MainUiState
) {

    if (showShimmer) {
        MediaHomeScreenShimmer(
            route = route,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp,
                    bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    } else {
        MediaHomeScreenSection(
            route = route,
            mainNavController = mainNavController,
            mainUiState = mainUiState
        )
    }
}
