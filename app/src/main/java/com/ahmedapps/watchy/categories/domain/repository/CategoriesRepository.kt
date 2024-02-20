package com.ahmedapps.watchy.categories.domain.repository

import com.ahmedapps.watchy.main.domain.models.Media

interface CategoriesRepository {
    suspend fun getActionAndAdventureCategory(): List<Media>
    suspend fun getDramaCategory(): List<Media>
    suspend fun getComedyCategory(): List<Media>
    suspend fun getSciFiFantasyCategory(): List<Media>
    suspend fun getAnimationCategory(): List<Media>
}










