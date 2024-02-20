package com.ahmedapps.watchy.util

import com.ahmedapps.watchy.BuildConfig

/**
 * @author Ahmed Guedmioui
 */

object BackendConstants {

    const val BACKEND_BASE_URL = BuildConfig.BACKEND_BASE_URL

    const val SIGN_UP = "signup"
    const val SIGN_IN = "signin"
    const val AUTHENTICATE = "authenticate"

    const val GET_LIKED_MEDIA_LIST = "get-liked-media-list/{email}"
    const val GET_BOOKMARKED_MEDIA_LIST = "get-bookmarked-media-list/{email}"
    const val GET_MEDIA_BY_ID = "get-media-by-id/{taskId}/{email}"
    const val UPSERT_MEDIA_TO_USER = "upsert-media-to-user"
    const val DELETE_MEDIA_FROM_USER = "delete-media-from-user"

}
