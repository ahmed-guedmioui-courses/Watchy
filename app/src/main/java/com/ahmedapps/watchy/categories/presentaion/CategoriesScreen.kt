package com.ahmedapps.watchy.categories.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.categories.presentaion.category_list.CategoryListScreen
import com.ahmedapps.watchy.categories.presentaion.ui_components.CategoryItemImage
import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.main.presentation.main.MainUiState
import com.ahmedapps.watchy.ui.theme.MediumRadius
import com.ahmedapps.watchy.ui.theme.Radius
import com.ahmedapps.watchy.ui.theme.font
import com.ahmedapps.watchy.util.Route

@Composable
fun CoreCategoriesScreen(
    mainNavController: NavController,
    mainUiState: MainUiState
) {

    val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
    val categoriesUiState = categoriesViewModel.categoriesUiState.collectAsState().value

    LaunchedEffect(key1 = true) {
        categoriesViewModel.onEvent(
            CategoriesUiEvents.SetDataAndLoad(
                moviesGenresList = mainUiState.moviesGenresList,
                tvGenresList = mainUiState.tvGenresList
            )
        )
    }

    val categoriesNavController = rememberNavController()
    NavHost(
        navController = categoriesNavController,
        startDestination = Route.CATEGORIES_SCREEN
    ) {

        composable(Route.CATEGORIES_SCREEN) {
            CategoriesScreen(
                categoriesNavController = categoriesNavController,
                categoriesUiState = categoriesUiState
            )
        }

        composable(Route.ACTION_AND_ADVENTURE_CATEGORY_SCREEN) {
            CategoryListScreen(
                route = Route.ACTION_AND_ADVENTURE_CATEGORY_SCREEN,
                mainNavController = mainNavController,
                categoriesUiState = categoriesUiState
            )
        }

        composable(Route.DRAMA_CATEGORY_SCREEN) {
            CategoryListScreen(
                route = Route.DRAMA_CATEGORY_SCREEN,
                mainNavController = mainNavController,
                categoriesUiState = categoriesUiState
            )
        }

        composable(Route.COMEDY_CATEGORY_SCREEN) {
            CategoryListScreen(
                route = Route.COMEDY_CATEGORY_SCREEN,
                mainNavController = mainNavController,
                categoriesUiState = categoriesUiState
            )
        }

        composable(Route.SCI_FI_AND_FANTASY_CATEGORY_SCREEN) {
            CategoryListScreen(
                route = Route.SCI_FI_AND_FANTASY_CATEGORY_SCREEN,
                mainNavController = mainNavController,
                categoriesUiState = categoriesUiState
            )
        }

        composable(Route.ANIMATION_CATEGORY_SCREEN) {
            CategoryListScreen(
                route = Route.ANIMATION_CATEGORY_SCREEN,
                mainNavController = mainNavController,
                categoriesUiState = categoriesUiState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    categoriesNavController: NavController,
    categoriesUiState: CategoriesUiState
) {

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.categories),
                    fontFamily = font,
                    fontSize = 20.sp
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.weight(1f)) {
                CategoryItem(
                    title = stringResource(id = R.string.action_and_adventure),
                    route = Route.ACTION_AND_ADVENTURE_CATEGORY_SCREEN,
                    mediaList = categoriesUiState.actionAndAdventureList,
                    categoriesNavController = categoriesNavController,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                CategoryItem(
                    title = stringResource(id = R.string.drama),
                    route = Route.DRAMA_CATEGORY_SCREEN,
                    mediaList = categoriesUiState.dramaList,
                    categoriesNavController = categoriesNavController,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                CategoryItem(
                    title = stringResource(id = R.string.comedy),
                    route = Route.COMEDY_CATEGORY_SCREEN,
                    mediaList = categoriesUiState.comedyList,
                    categoriesNavController = categoriesNavController,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                CategoryItem(
                    title = stringResource(id = R.string.sci_fi_and_fantasy),
                    route = Route.SCI_FI_AND_FANTASY_CATEGORY_SCREEN,
                    mediaList = categoriesUiState.sciFiAndFantasyList,
                    categoriesNavController = categoriesNavController,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                CategoryItem(
                    title = stringResource(id = R.string.animation),
                    mediaList = categoriesUiState.animationList,
                    route = Route.ANIMATION_CATEGORY_SCREEN,
                    categoriesNavController = categoriesNavController,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

        }
    }
}


@Composable
fun CategoryItem(
    title: String,
    mediaList: List<Media>,
    route: String,
    categoriesNavController: NavController
) {
    if (mediaList.isEmpty()) {
        Box(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(0.9f)
                .padding(
                    top = 20.dp,
                    bottom = 12.dp
                )
                .clip(RoundedCornerShape(MediumRadius))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = font,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        }

    } else {

        CategoryItemImage(
            title = title,
            route = route,
            media = mediaList[0],
            categoriesNavController = categoriesNavController,
        )
    }
}



