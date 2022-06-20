package com.honestboook.focusbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.honestboook.focusbook.model.Site
import com.honestboook.focusbook.data.SiteDatabase
import com.honestboook.focusbook.repository.SiteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SiteViewModel(application: Application): AndroidViewModel(application){
    val allSites: LiveData<List<Site>>
    private val siteRepository: SiteRepository

    init {
        val siteDao = SiteDatabase.getDatabase(application).siteDao()
        siteRepository = SiteRepository(siteDao)
        allSites = siteRepository.allSites
    }

    fun addSite(site: Site) {
        viewModelScope.launch(Dispatchers.IO) {
            siteRepository.addSite(site)
        }
    }

    fun updateSite(site: Site) {
        viewModelScope.launch(Dispatchers.IO) {
            siteRepository.updateSite(site)
        }
    }

    fun deleteSite(site: Site) {
        viewModelScope.launch(Dispatchers.IO) {
            siteRepository.deleteSite(site)
        }
    }

    fun deleteAllSites() {
        viewModelScope.launch(Dispatchers.IO) {
            siteRepository.deleteAllSites()
        }
    }
}