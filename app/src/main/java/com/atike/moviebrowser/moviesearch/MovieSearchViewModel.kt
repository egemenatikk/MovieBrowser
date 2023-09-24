/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.moviesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atike.moviebrowser.SingleLiveEvent
import com.atike.moviebrowser.network.Movie
import com.atike.moviebrowser.network.MovieApi
import kotlinx.coroutines.launch

class MovieSearchViewModel(movieKeyword: String) : ViewModel() {

    private val _movies = SingleLiveEvent<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _navigateToSelectedMovie = SingleLiveEvent<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    init {
        getMovieSearch(movieKeyword)
    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    private fun getMovieSearch(movieKeyword: String) {
        viewModelScope.launch {
            _movies.value = runCatching {
                MovieApi.retrofitService.getMovieSearch(query = movieKeyword).results
            }.getOrNull().orEmpty()
        }
    }
}
