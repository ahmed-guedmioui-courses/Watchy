package com.ahmedapps.watchy.main.data.repository

import android.app.Application
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmedapps.watchy.main.data.local.media.MediaDatabase
import com.ahmedapps.watchy.main.data.mapper.toMedia
import com.ahmedapps.watchy.main.data.mapper.toMediaEntity
import com.ahmedapps.watchy.main.data.remote.api.MediaApi
import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.main.domain.repository.MainRepository
import com.ahmedapps.watchy.util.APIConstants
import com.ahmedapps.watchy.util.APIConstants.GET_TAG
import com.ahmedapps.watchy.util.APIConstants.TRENDING
import com.ahmedapps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val mediaApi: MediaApi,
    private val application: Application,
    private val favoritesRepository: FavoritesRepository,
    mediaDb: MediaDatabase,
) : MainRepository {

    private val mediaDao = mediaDb.mediaDao

    override suspend fun upsertMediaList(mediaList: List<Media>) {
        mediaDao.upsertMediaList(mediaList.map { it.toMediaEntity() })
    }

    override suspend fun upsertMediaItem(media: Media) {
        mediaDao.upsertMediaItem(media.toMediaEntity())
    }

    override suspend fun insertSearchedMedia(media: Media) {
        mediaDao.upsertMediaItem(media.toMediaEntity())
    }

    override suspend fun getMediaById(id: Int): Media {
        return mediaDao.getMediaById(id).toMedia()
    }

    override suspend fun getMediaListByCategory(category: String): List<Media> {
        return mediaDao.getMediaListByCategory(category).map { it.toMedia() }
    }

    override suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByTypeAndCategory(type, category)

            val shouldJustLoadFromCache =
                localMediaList.isNotEmpty() && !fetchFromRemote && !isRefresh
            if (shouldJustLoadFromCache) {

                emit(Resource.Success(localMediaList.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            var searchPage = page
            if (isRefresh) {
                searchPage = 1
            }

            val remoteMediaList = try {
                mediaApi.getMoviesAndTvSeriesList(
                    type, category, searchPage, apiKey
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

            remoteMediaList?.let { mediaDtoList ->
                val entities = mediaDtoList.map { mediaDto ->

                    val favoriteMediaEntity =
                        favoritesRepository.getFavoriteMediaItemById(mediaDto.id ?: 0)

                    var isFavorite = false
                    var isInWatchList = false

                    if (favoriteMediaEntity != null) {
                        isFavorite = favoriteMediaEntity.isLiked
                        isInWatchList = favoriteMediaEntity.isBookmarked
                    }

                    mediaDto.toMediaEntity(
                        type = type,
                        category = category,
                        isFavorite = isFavorite,
                        isInWatchlist = isInWatchList
                    )
                }

                // when we refresh we want new data
                if (isRefresh) {
                    mediaDao.deleteMediaByTypeAndCategory(type, category)
                }
                mediaDao.upsertMediaList(entities)

                emit(Resource.Success(entities.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(application.getString(R.string.couldnt_load_data)))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTrendingList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByCategory(TRENDING)

            val shouldJustLoadFromCache = localMediaList.isNotEmpty() && !fetchFromRemote
            if (shouldJustLoadFromCache) {

                emit(Resource.Success(localMediaList.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            var searchPage = page

            if (isRefresh) {
                searchPage = 1
            }

            val remoteMediaList = try {
                mediaApi.getTrendingList(
                    type, time, searchPage, apiKey
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

            remoteMediaList?.let { mediaList ->
                val entities = mediaList.map { mediaDto ->

                    val favoriteMediaEntity =
                        favoritesRepository.getFavoriteMediaItemById(mediaDto.id ?: 0)

                    var isFavorite = false
                    var isInWatchList = false

                    if (favoriteMediaEntity != null) {
                        isFavorite = favoriteMediaEntity.isLiked
                        isInWatchList = favoriteMediaEntity.isBookmarked
                    }

                    mediaDto.toMediaEntity(
                        type = APIConstants.MOVIE,
                        category = TRENDING,
                        isFavorite = isFavorite,
                        isInWatchlist = isInWatchList
                    )
                }

                // when we refresh we want new data
                if (isRefresh) {
                    mediaDao.deleteTrendingMediaList(TRENDING)
                }
                mediaDao.upsertMediaList(entities)

                emit(Resource.Success(entities.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(application.getString(R.string.couldnt_load_data)))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun clearMediaDb() {
        mediaDao.deleteAllMediaItems()
    }

}










