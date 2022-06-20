package com.honestboook.focusbook.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.honestboook.focusbook.data.SiteDao
import com.honestboook.focusbook.model.Site
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

class SiteRepository(private val siteDao: SiteDao) {
    val allSites: LiveData<List<Site>> = siteDao.getSites()

    suspend fun addSite(site: Site) {
        siteDao.insertSites(listOf(site))
        Log.d("success", "successfully inserted data")
    }

    suspend fun updateSite(site: Site) {
        siteDao.updateSite(site)
        Log.d("success", "successfully updated data")
    }

    suspend fun deleteSite(site: Site) {
        siteDao.deleteSite(site)
    }

    suspend fun deleteAllSites() {
        siteDao.deleteAllSites()
    }
}