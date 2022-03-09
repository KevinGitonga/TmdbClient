package com.a1g0.tmdbclient.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a1g0.tmdbclient.data.local.dao.MoviesDao
import com.a1g0.tmdbclient.data.model.MovieDB
import kotlinx.coroutines.Dispatchers
import com.a1g0.tmdbclient.data.model.Resource
import com.a1g0.tmdbclient.data.repository.NetworkRepository
import com.a1g0.tmdbclient.utils.Constants
import com.a1g0.tmdbclient.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository : NetworkRepository,
    private val moviesDao: MoviesDao,
    private val context: Context
) : ViewModel() {
    fun loadNowPlayingMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            if (NetworkUtils.isOnline(context)){
                moviesDao.clearLocalData()
                val apiResponse = repository.getNowPlayingMovies()

                val moviesLocalCopy = apiResponse.results.map { it-> MovieDB(it.id,it.poster_path.toString(),it.overview.toString(),it.title.toString(),it.backdrop_path.toString(),Constants.NowPlaying,
                    it.vote_average!!
                ) }
                moviesDao.insertMovies(moviesLocalCopy)

                emit(Resource.success(moviesLocalCopy))
            }
            else{
                val localMoviesData = moviesDao.getAllMovies(Constants.NowPlaying)
                emit(Resource.success(localMoviesData))
            }



        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadUpcoming() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            if (NetworkUtils.isOnline(context)){
                moviesDao.clearLocalData()
                val apiResponse = repository.getUpcomingMovies()

                val moviesLocalCopy = apiResponse.results.map { it-> MovieDB(it.id,it.poster_path.toString(),it.overview.toString(),it.title.toString(),it.backdrop_path.toString(),Constants.Upcoming,
                    it.vote_average!!
                ) }
                moviesDao.insertMovies(moviesLocalCopy)

                emit(Resource.success(moviesLocalCopy))
            }
            else{
               val localMoviesData = moviesDao.getAllMovies(Constants.Upcoming)
                emit(Resource.success(localMoviesData))
            }



        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadPopular() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote

            if (NetworkUtils.isOnline(context)){
                moviesDao.clearLocalData()
                val apiResponse = repository.getPopularMovies()
                val moviesLocalCopy = apiResponse.results.map { it-> MovieDB(it.id,it.poster_path.toString(),it.overview.toString(),it.title.toString(),it.backdrop_path.toString(),Constants.Popular,it.vote_average!!) }
                moviesDao.insertMovies(moviesLocalCopy)
                emit(Resource.success(moviesLocalCopy))
            }
            else{
                val localMoviesData = moviesDao.getAllMovies(Constants.Popular)
                emit(Resource.success(localMoviesData))
            }

        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadTopRated() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            if(NetworkUtils.isOnline(context)){
                moviesDao.clearLocalData()
                val apiResponse = repository.getTopRatedMovies()

                val moviesLocalCopy = apiResponse.results.map { it-> MovieDB(it.id,it.poster_path.toString(),it.overview.toString(),it.title.toString(),it.backdrop_path.toString(),Constants.TopRated,it.vote_average!!) }
                moviesDao.insertMovies(moviesLocalCopy)

                emit(Resource.success(moviesLocalCopy))
            }
            else{
                val localMoviesData = moviesDao.getAllMovies(Constants.TopRated)
                emit(Resource.success(localMoviesData))
            }

        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }


}