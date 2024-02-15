package com.ahmedapps.watchy.favorites.data.repository

import com.ahmedapps.watchy.favorites.data.local.FavoriteMediaDatabase
import com.ahmedapps.watchy.favorites.data.local.FavoriteMediaEntity
import com.ahmedapps.watchy.favorites.data.mapper.toFavoriteMediaEntity
import com.ahmedapps.watchy.favorites.data.mapper.toMedia
import com.ahmedapps.watchy.favorites.data.mapper.toMediaRequest
import com.ahmedapps.watchy.favorites.domain.repository.BackendFavoriteMediaRepository
import com.ahmedapps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmedapps.watchy.main.data.local.media.MediaDatabase
import com.ahmedapps.watchy.main.data.mapper.toMediaEntity
import com.ahmedapps.watchy.main.domain.models.Media
import com.ahmedapps.watchy.util.APIConstants.GET_TAG
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    favoriteMediaDatabase: FavoriteMediaDatabase,
    private val backendFavoriteMediaRepository: BackendFavoriteMediaRepository,
    mediaDb: MediaDatabase
) : FavoritesRepository {

    private val mediaDao = mediaDb.mediaDao

    private val favoriteMediaDao = favoriteMediaDatabase.favoriteMediaDao

    private val _favoritesDbUpdateEvent = Channel<Boolean>()

    override suspend fun favoritesDbUpdateEventFlow(): Flow<Boolean> =
        _favoritesDbUpdateEvent.receiveAsFlow()

    override suspend fun upsertFavoriteMediaItem(media: Media) {

        favoriteMediaDao.upsertFavoriteMediaItem(media.toFavoriteMediaEntity())
        syncFavoriteMediaItems()

        _favoritesDbUpdateEvent.send(true)
    }

    override suspend fun deleteFavoriteMediaItem(media: Media) {

        val favoriteMediaEntity = media.toFavoriteMediaEntity().copy(isDeletedLocally = true)

        favoriteMediaDao.upsertFavoriteMediaItem(favoriteMediaEntity)
        syncFavoriteMediaItems()

        _favoritesDbUpdateEvent.send(true)
    }

    override suspend fun getFavoriteMediaItemById(id: Int): Media? {

        val favoriteMediaEntity = favoriteMediaDao.getFavoriteMediaItemById(id)
        favoriteMediaEntity?.let {
            return it.toMedia()
        }

        val remoteMedia = backendFavoriteMediaRepository.getMediaById(id)

        remoteMedia?.let {
            favoriteMediaDao.upsertFavoriteMediaItem(
                remoteMedia.toFavoriteMediaEntity()
            )
            mediaDao.upsertMediaItem(
                remoteMedia.toMedia().toMediaEntity()
            )

            return favoriteMediaDao.getFavoriteMediaItemById(id)?.toMedia()
        }

        return null

    }

    override suspend fun getLikedMediaItemList(): List<Media> {

        val localLikedMediaItemList = favoriteMediaDao.getLikedMediaItemList()
        if (localLikedMediaItemList.isNotEmpty()) {
            return localLikedMediaItemList.map { it.toMedia() }
        }

        val remoteLikedMediaItemList = backendFavoriteMediaRepository.getLikedMediaItemList()
        remoteLikedMediaItemList?.let {

            favoriteMediaDao.upsertFavoriteMediaList(
                remoteLikedMediaItemList.map { it.toFavoriteMediaEntity() }
            )
            mediaDao.upsertMediaList(
                remoteLikedMediaItemList.map { it.toMedia().toMediaEntity() }
            )

            return favoriteMediaDao.getLikedMediaItemList().map { it.toMedia() }
        }

        return emptyList()
    }

    override suspend fun getBookmarkedMediaItemList(): List<Media> {

        val bookmarkedLikedMediaItemList = favoriteMediaDao.getBookmarkedItemList()
        if (bookmarkedLikedMediaItemList.isNotEmpty()) {
            return bookmarkedLikedMediaItemList.map { it.toMedia() }
        }

        val remoteBookmarkedMediaItemList =
            backendFavoriteMediaRepository.getBookmarkedMediaItemList()
        remoteBookmarkedMediaItemList?.let {

            favoriteMediaDao.upsertFavoriteMediaList(
                remoteBookmarkedMediaItemList.map { it.toFavoriteMediaEntity() }
            )
            mediaDao.upsertMediaList(
                remoteBookmarkedMediaItemList.map { it.toMedia().toMediaEntity() }
            )

            return favoriteMediaDao.getBookmarkedItemList().map { it.toMedia() }
        }

        return emptyList()
    }

    override suspend fun syncFavoriteMediaItems() {
        val favoriteMediaEntities = favoriteMediaDao.getAllFavoriteMediaItem()

        Timber.tag(GET_TAG).d("syncFavoriteMediaItems() ${favoriteMediaEntities.size}")

        favoriteMediaEntities.forEach { favoriteMediaEntity ->
            if (favoriteMediaEntity.isDeletedLocally) {
                handleLocallyDeletedMedia(favoriteMediaEntity)
            } else if (!favoriteMediaEntity.isSynced) {
                handleUnSyncedMedia(favoriteMediaEntity)
            }
        }
    }

    private suspend fun handleLocallyDeletedMedia(favoriteMediaEntity: FavoriteMediaEntity) {
        val wasDeleted = backendFavoriteMediaRepository.deleteMediaFromUser(
            favoriteMediaEntity.mediaId
        )
        if (wasDeleted) {
            favoriteMediaDao.deleteFavoriteMediaItemById(favoriteMediaEntity)
        }
    }

    private suspend fun handleUnSyncedMedia(favoriteMediaEntity: FavoriteMediaEntity) {
        val wasAcknowledged = backendFavoriteMediaRepository.upsertMediaToUser(
            favoriteMediaEntity.toMediaRequest()
        )
        if (wasAcknowledged) {
            favoriteMediaDao.upsertFavoriteMediaItem(
                favoriteMediaEntity.copy(isSynced = true)
            )
        }
    }

    override suspend fun clearFavoritesDb() {
        favoriteMediaDao.deleteAllFavoriteMediaItems()
    }
}










