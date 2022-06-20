package com.honestboook.focusbook.repository

import com.honestboook.focusbook.model.Site
import retrofit2.Response
import retrofit2.http.GET

interface SiteApi {
    @GET("something.json")
    suspend fun getSites(): Response<List<Site>>
}