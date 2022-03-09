package com.a1g0.tmdbclient.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.a1g0.tmdbclient.BuildConfig
import com.a1g0.tmdbclient.data.api.NetworkService
import com.a1g0.tmdbclient.data.api.SafeApiRequest
import com.a1g0.tmdbclient.ui.paging.NowPlayingPagingSource
import com.a1g0.tmdbclient.ui.paging.PopularPagingSource
import com.a1g0.tmdbclient.ui.paging.TopRatedPagingSource
import com.a1g0.tmdbclient.ui.paging.UpcomingPagingSource
import com.a1g0.tmdbclient.utils.Constants
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkApi: NetworkService
) : SafeApiRequest() {

    // Get Movie Details
    suspend fun getMovieDetails(movie_id : Int) = apiRequest {
        networkApi.getMovieById(movie_id, BuildConfig.DATA_API_KEY)
    }

    // Now Playing Movies
    suspend fun getNowPlayingMovies() = apiRequest {
        networkApi.getNowPlayingMovies(BuildConfig.DATA_API_KEY)
    }

    // Get Videos
    suspend fun getVideos(movie_id : Int) = apiRequest {
        networkApi.getVideos(movie_id, BuildConfig.DATA_API_KEY)
    }

    // Latest Movies
    suspend fun getLatestMovies() = apiRequest {
        networkApi.getLatestMovies(BuildConfig.DATA_API_KEY)
    }

    // Upcoming Movies
    suspend fun getUpcomingMovies() = apiRequest {
        networkApi.getUpcomingMovies(BuildConfig.DATA_API_KEY)
    }

    // Popular Movies
    suspend fun getPopularMovies() = apiRequest {
        networkApi.getPopularMovies(BuildConfig.DATA_API_KEY)
    }

    // Top Rated Movies
    suspend fun getTopRatedMovies() = apiRequest {
        networkApi.getTopRatedMovies(BuildConfig.DATA_API_KEY)
    }

    // Movie Credits
    suspend fun getMovieCredits(movie_id : Int) = apiRequest {
        networkApi.getMovieCredits(movie_id, BuildConfig.DATA_API_KEY)
    }

    // Get Similar Movies
    suspend fun getSimilarMovies(movie_id : Int) = apiRequest {
        networkApi.getSimilarMovies(movie_id, BuildConfig.DATA_API_KEY)
    }

    // Get Person
    suspend fun getPerson(personId : Int) = apiRequest {
        networkApi.getPerson(personId, BuildConfig.DATA_API_KEY)
    }

    // Person Movie Credits
    suspend fun getPersonMovieCredits(personId : Int) = apiRequest {
        networkApi.getPersonMovieCredits(personId, BuildConfig.DATA_API_KEY)
    }

    // Search Movie
    suspend fun searchMovie(query: String, page: Int) = apiRequest {
        networkApi.searchMovie(query, page, BuildConfig.DATA_API_KEY)
    }

    fun getPopularMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                PopularPagingSource(networkApi)
            }
        ).liveData

    fun getUpcomingMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                UpcomingPagingSource(networkApi)
            }
        ).liveData

    fun getTopRatedMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                TopRatedPagingSource(networkApi)
            }
        ).liveData

    fun getNowPlayingMoviesPaged()=
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                NowPlayingPagingSource(networkApi)
            }
        ).liveData

    }