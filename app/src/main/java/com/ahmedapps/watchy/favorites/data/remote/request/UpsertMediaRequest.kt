package com.ahmedapps.watchy.favorites.data.remote.request

data class UpsertMediaRequest(
    val mediaRequest: MediaRequest,
    val email: String,
)