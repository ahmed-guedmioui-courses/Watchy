package com.ahmedapps.watchy.ui.ui_shared_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ahmedapps.watchy.main.data.remote.api.MediaApi
import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.ui.theme.Radius
import com.ahmedapps.watchy.util.Route

@Composable
fun MediaItemImage(
    modifier: Modifier = Modifier,
    backdropPath: Boolean = false,
    media: Media,
    mainNavController: NavController
) {

    val imageUrl = if (backdropPath) {
        "${MediaApi.IMAGE_BASE_URL}${media.backdropPath}"
    } else {
        "${MediaApi.IMAGE_BASE_URL}${media.posterPath}"
    }

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Radius.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                mainNavController.navigate(
                    "${Route.CORE_DETAILS_SCREEN}?id=${media.mediaId}"
                )
            }
    ) {

        if (imageState is AsyncImagePainter.State.Success) {
            val imageBitmap = imageState.result.drawable.toBitmap()
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = media.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                imageVector = Icons.Rounded.ImageNotSupported,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = media.title
            )
        }


        if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
                    .scale(0.5f)
            )
        }
    }
}

