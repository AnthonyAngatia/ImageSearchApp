package com.codinginflow.imagesearchapp.data

import androidx.paging.PagingSource
import com.codinginflow.imagesearchapp.api.UnsplashApiService
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1;
//We wont use hilt injection bacause @query is determined at runtime

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApiService,
    private val query: String
): PagingSource<Int, UnsplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhoto(query, position, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data=photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position-1,
                nextKey = if (photos.isEmpty()) null else position+1
            )
        }catch (exception:IOException){
            LoadResult.Error(exception) //eg Problem with the network
        }catch (exception:HttpException){
            LoadResult.Error(exception)// invalid access token, wrong params to the server etc
        }
    }
}