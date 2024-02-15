package com.ahmedapps.watchy.main.presentation.main.ui_componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.ui.theme.Radius
import com.ahmedapps.watchy.ui.ui_shared_components.shimmerEffect
import com.ahmedapps.watchy.ui.theme.font
import com.ahmedapps.watchy.util.Route

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun MediaHomeScreenShimmer(
    route: String,
    paddingEnd: Dp,
    modifier: Modifier = Modifier,
) {

    val title = when (route) {
        Route.TRENDING_NOW_SCREEN -> {
            stringResource(id = R.string.trending)
        }

        Route.TV_SERIES_SCREEN -> {
            stringResource(id = R.string.tv_series)
        }

        else -> {
            stringResource(R.string.popular_movies)
        }
    }

    Column (
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 22.dp,
            ),
            fontWeight = FontWeight.Bold,
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = font,
            fontSize = 20.sp
        )

        LazyRow {
            items(10) {
                Box(
                    modifier = modifier
                        .padding(
                            end = if (it == 9) paddingEnd else 0.dp
                        )
                        .clip(RoundedCornerShape(Radius.dp))
                        .shimmerEffect(false)
                )
            }
        }
    }
}
