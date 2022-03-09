package com.a1g0.tmdbclient.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import com.a1g0.tmdbclient.data.model.Resource
import com.a1g0.tmdbclient.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val repository : NetworkRepository
): ViewModel() {

    fun getPerson(person_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = repository.getPerson(person_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getPersonMovieCredits(person_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch Data from Api
            val apiResponse = repository.getPersonMovieCredits(person_id)
            emit(Resource.success(apiResponse))
        } catch (e : Exception) {
            if (e is SocketTimeoutException) {
                emit(Resource.error("Something went wrong!"))
            }
        }

    }


}