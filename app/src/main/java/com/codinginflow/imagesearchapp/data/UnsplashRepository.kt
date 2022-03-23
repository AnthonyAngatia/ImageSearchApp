package com.codinginflow.imagesearchapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.codinginflow.imagesearchapp.api.UnsplashApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApiService: UnsplashApiService) {

    fun getSearchResults(query:String): LiveData<PagingData<UnsplashPhoto>> =
         Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApiService,query)}
        ).liveData

}