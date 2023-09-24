/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.movieoverview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atike.moviebrowser.SingleLiveEvent
import com.atike.moviebrowser.network.Movie
import com.atike.moviebrowser.network.MovieApi
import kotlinx.coroutines.launch

class MovieOverviewViewModel(movieType: MovieType) : ViewModel() {

    private val _movies = SingleLiveEvent<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _navigateToSelectedMovie = SingleLiveEvent<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    init {
        when (movieType) {
            MovieType.UPCOMING -> getUpcomingMovies()
            MovieType.NOW_PLAYING -> getNowPlayingMovies()
            MovieType.POPULAR -> getPopularMovies()
            MovieType.TOP_RATED -> getTopRatedMovies()
        }

    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            _movies.value = runCatching {
                MovieApi.retrofitService.getUpcomingMovies().results
            }.getOrNull().orEmpty()
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            _movies.value = runCatching {
                MovieApi.retrofitService.getNowPlayingMovies().results
            }.getOrNull().orEmpty()
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _movies.value = runCatching {
                MovieApi.retrofitService.getPopularMovies().results
            }.getOrNull().orEmpty()
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            _movies.value = runCatching {
                MovieApi.retrofitService.getTopRatedMovies().results
            }.getOrNull().orEmpty()
        }
    }
}
