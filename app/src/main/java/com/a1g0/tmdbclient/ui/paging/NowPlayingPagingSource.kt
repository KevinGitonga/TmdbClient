package com.a1g0.tmdbclient.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.a1g0.tmdbclient.BuildConfig
import com.a1g0.tmdbclient.data.api.NetworkService
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.utils.Constants
import retrofit2.HttpException
import java.io.IOException

private const val DEFAULT_PAGE = 1

class NowPlayingPagingSource(
        private val networkApi : NetworkService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: DEFAULT_PAGE

        return try {

            val response = networkApi.getNowPlayingMovies(BuildConfig.DATA_API_KEY, position)
            val data = response.body()!!.results

            LoadResult.Page(
                    data = data,
                    prevKey = if(position == DEFAULT_PAGE) null else position-1,
                    nextKey = if(data.isEmpty()) null else position+1
            )

        }
        catch(exception : IOException) {
            LoadResult.Error(exception)
        }
        catch(exception : HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return 1
    }

}