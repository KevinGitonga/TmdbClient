package com.a1g0.tmdbclient.ui.details

import androidx.lifecycle.*
import kotlinx.coroutines.*
import com.a1g0.tmdbclient.data.local.dao.MoviesDao
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.data.model.MovieDB
import com.a1g0.tmdbclient.data.model.Resource
import com.a1g0.tmdbclient.data.model.VideoResponse
import com.a1g0.tmdbclient.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber.Forest.e
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val databaseDao : MoviesDao,
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _name = MutableLiveData("Movie Name")
    private val _movie = MutableLiveData<Movie>()
    private val _videos = MutableLiveData<VideoResponse>()

    var bookmark = MutableLiveData(false)
    var movieName : MutableLiveData<String> = _name
    var movie : MutableLiveData<Movie> = _movie
    var videos : MutableLiveData<VideoResponse> = _videos

    fun getMovieDetails(movie_id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = networkRepository.getMovieDetails(movie_id)
            movie.postValue(apiResponse)
        }
    }

    fun loadCast(movie_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = networkRepository.getMovieCredits(movie_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadSimilar(movie_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = networkRepository.getSimilarMovies(movie_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getVideos(movie_id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResponse = networkRepository.getVideos(movie_id)
                videos.postValue(apiResponse)
            } catch (e: Exception) {
                e(e)
            }
        }
    }

}