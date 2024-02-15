package com.ahmedapps.watchy.search.data.remote.api

import com.ahmedapps.watchy.main.data.remote.dto.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/multi")
    suspend fun getSearchList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MediaListDto?

}

