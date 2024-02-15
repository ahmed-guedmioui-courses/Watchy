package com.ahmedapps.watchy.ui.ui_shared_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.main.domain.models.Media

import com.ahmedapps.watchy.ui.theme.font

@Composable
fun AutoSwipeSection(
    title: String,
    backdropPath: Boolean = false,
    showSeeAll: Boolean = false,
    route: String = "",
    navController: NavController? = null,
    mainNavController: NavController,
    mediaList: List<Media>
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = font,
                fontSize = 20.sp
            )

            if (showSeeAll) {
                Text(
                    modifier = Modifier
                        .alpha(0.7f)
                        .clickable { navController?.navigate(route) },
                    text = stringResource(id = R.string.see_all),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontFamily = font,
                    fontSize = 14.sp,
                )
            }

        }

        AutoSwipeImagePager(
            backdropPath = backdropPath,
            mediaList = mediaList,
            mainNavController = mainNavController,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(0.9f)
        )
    }

}
