/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atike.moviebrowser.databinding.FragmentMovieSearchBinding
import com.atike.moviebrowser.movieoverview.MovieGridAdapter

class MovieSearchFragment : Fragment() {

    private val viewModel: MovieSearchViewModel by viewModels {
        MovieSearchViewModelFactory(
            MovieSearchFragmentArgs.fromBundle(requireArguments()).movieKeyword
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.fragmentMovieSearchRecyclerView.adapter = MovieGridAdapter(
            MovieGridAdapter.OnClickListener {
                viewModel.displayMovieDetails(it)
            }
        )

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner) {
            if (null != it) {
                findNavController().navigate(MovieSearchFragmentDirections.actionShowMovieDetail(it.id!!))
            }
        }
        return binding.root
    }
}
