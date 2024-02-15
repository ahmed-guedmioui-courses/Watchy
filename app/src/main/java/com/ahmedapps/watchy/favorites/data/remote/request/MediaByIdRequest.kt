package com.ahmedapps.watchy.favorites.data.remote.request

data class MediaByIdRequest(
    val mediaId: Int,
    val email: String
)