package com.ahmedapps.watchy.search.data.repository

import android.app.Application
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmedapps.watchy.main.data.mapper.toMedia
import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.search.data.remote.api.SearchApi
import com.ahmedapps.watchy.search.domain.repository.SearchRepository
import com.ahmedapps.watchy.util.APIConstants
import com.ahmedapps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val application: Application,
    private val favoritesRepository: FavoritesRepository
) : SearchRepository {

    override suspend fun getSearchList(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {

        return flow {
            emit(Resource.Loading(true))

            val remoteMediaList = try {
                searchApi.getSearchList(
                    query, page, apiKey
                )?.results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldnt_load_data)))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldnt_load_data)))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList?.let {
                val mediaList = it.map { mediaDto ->

                    val favoriteMedia =
                        favoritesRepository.getFavoriteMediaItemById(mediaDto.id ?: 0)

                    var isFavorite = false
                    var isInWatchList = false

                    favoriteMedia?.let { media ->
                        isFavorite = media.isLiked
                        isInWatchList = media.isBookmarked
                    }

                    mediaDto.toMedia(
                        type = APIConstants.MOVIE,
                        category = mediaDto.category ?: "",
                        isFavorite = isFavorite,
                        isInWatchlist = isInWatchList
                    )
                }

                emit(Resource.Success(mediaList))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(application.getString(R.string.couldnt_load_data)))
            emit(Resource.Loading(false))
        }
    }
}










