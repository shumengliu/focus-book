package com.honestboook.focusbook.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.honestboook.focusbook.model.Site

@Dao
interface SiteDao {
    @Insert
    suspend fun insertSites(sites: List<Site>)

    @Update
    suspend fun updateSite(site: Site)

    @Delete
    suspend fun deleteSite(site: Site)

    @Query("DELETE FROM sites")
    suspend fun deleteAllSites()

    @Query("SELECT * FROM Sites")
    fun getSites(): LiveData<List<Site>>

    @Query("SELECT url FROM Sites")
    suspend fun getBlockList(): List<String>

    @Query("SELECT count(*) FROM Sites")
    suspend fun getCount(): Int
}