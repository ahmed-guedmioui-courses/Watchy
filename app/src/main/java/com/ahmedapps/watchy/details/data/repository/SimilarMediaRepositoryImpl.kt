package com.ahmedapps.watchy.details.data.repository

import android.app.Application
import com.ahmedapps.watchy.R
import com.ahmedapps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmedapps.watchy.details.data.remote.api.DetailsApi
import com.ahmedapps.watchy.details.domain.repository.SimilarMediaRepository
import com.ahmedapps.watchy.main.data.mapper.toMedia
import com.ahmedapps.watchy.main.data.remote.dto.MediaDto
import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.main.domain.repository.MainRepository
import com.ahmedapps.watchy.util.APIConstants
import com.ahmedapps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimilarMediaRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val application: Application,
    private val mainRepository: MainRepository,
    private val favoritesRepository: FavoritesRepository
) : SimilarMediaRepository {

    override suspend fun getSimilarMediaList(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>> {

        return flow {

            emit(Resource.Loading(true))

            val media = mainRepository.getMediaById(id = id)

            val doesSimilarMediaListExist = media.similarMediaList.isNotEmpty()

            if (!isRefresh && doesSimilarMediaListExist) {

                val similarMediaListIds = media.similarMediaList

                val similarMediaList = ArrayList<Media>()
                for (i in similarMediaListIds.indices) {
                    similarMediaList.add(mainRepository.getMediaById(similarMediaListIds[i]))
                }

                emit(Resource.Success(similarMediaList))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteSimilarMediaList = fetchRemoteForSimilarMediaList(
                type = media.mediaType,
                id = id,
                page = page,
                apiKey = apiKey
            )

            remoteSimilarMediaList?.let { similarMediaDtos ->

                val similarMediaListIntIds = ArrayList<Int>()
                for (i in similarMediaDtos.indices) {
                    similarMediaListIntIds.add(similarMediaDtos[i].id ?: 0)
                }

                mainRepository.upsertMediaItem(
                    media.copy(
                        similarMediaList = similarMediaListIntIds
                    )
                )

                val similarMediaList = similarMediaDtos.map { mediaDto ->
                    val favoriteMedia =
                        favoritesRepository.getFavoriteMediaItemById(mediaDto.id ?: 0)

                    var isFavorite = false
                    var isInWatchList = false

                    favoriteMedia?.let {
                        isFavorite = it.isLiked
                        isInWatchList = it.isBookmarked
                    }

                    mediaDto.toMedia(
                        type = APIConstants.MOVIE,
                        category = "similar_to_${media.mediaId}",
                        isFavorite = isFavorite,
                        isInWatchlist = isInWatchList
                    )
                }

                mainRepository.upsertMediaList(similarMediaList)

                emit(
                    Resource.Success(
                        mainRepository.getMediaListByCategory("similar_to_${media.mediaId}")
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(application.getString(R.string.couldnt_load_similar_media)))
            emit(Resource.Loading(false))
        }
    }

    private suspend fun fetchRemoteForSimilarMediaList(
        type: String,
        id: Int,
        page: Int,
        apiKey: String
    ): List<MediaDto>? {

        val remoteSimilarMediaList = try {
            detailsApi.getSimilarMediaList(
                type = type,
                id = id,
                page = page,
                apiKey = apiKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }

        return remoteSimilarMediaList?.results

    }
}









