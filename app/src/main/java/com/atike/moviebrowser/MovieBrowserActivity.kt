/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.atike.moviebrowser.moviedetail.MovieDetailFragment
import com.atike.moviebrowser.moviedetail.MovieDetailFragmentDirections
import com.atike.moviebrowser.movieoverview.MovieOverviewContainerFragmentDirections
import com.atike.moviebrowser.movieoverview.MovieOverviewFragment
import com.atike.moviebrowser.moviesearch.MovieSearchFragmentDirections

class MovieBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_browser)
        val editText: EditText = findViewById(R.id.activityMovieBrowserSearchEditText)
        val searchButton: Button = findViewById(R.id.activityMovieBrowserSearchButton)
        searchButton.setOnClickListener {
            val currentFragment = supportFragmentManager
                .findFragmentById(R.id.activityMovieBrowserFragmentContainerView)

            when (currentFragment) {
                is MovieOverviewFragment -> findNavController(
                    R.id.activityMovieBrowserFragmentContainerView
                ).navigate(MovieOverviewContainerFragmentDirections.actionShowMovieSearch(editText.text.toString()))

                is MovieDetailFragment -> findNavController(
                    R.id.activityMovieBrowserFragmentContainerView
                ).navigate(MovieDetailFragmentDirections.actionShowMovieSearch(editText.text.toString()))

                else -> findNavController(
                    R.id.activityMovieBrowserFragmentContainerView
                ).navigate(MovieSearchFragmentDirections.actionShowMovieSearch(editText.text.toString()))
            }

            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

}
