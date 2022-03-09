package com.a1g0.tmdbclient.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.a1g0.tmdbclient.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel @Inject constructor(
    private val repository : NetworkRepository
) : ViewModel() {

    fun fetchPopular() =
        repository.getPopularMovieResult().cachedIn(viewModelScope)

    fun fetchUpcoming() =
        repository.getUpcomingMovieResult().cachedIn(viewModelScope)

    fun fetchTopRated() =
        repository.getTopRatedMovieResult().cachedIn(viewModelScope)

    fun fetchNowPlaying() =
        repository.getNowPlayingMoviesPaged().cachedIn(viewModelScope)
}