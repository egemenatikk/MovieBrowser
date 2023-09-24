/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.movieoverview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atike.moviebrowser.databinding.FragmentMovieOverviewBinding

class MovieOverviewFragment : Fragment() {

    private val viewModel: MovieOverviewViewModel by viewModels {
        MovieOverviewViewModelFactory(requireArguments().getParcelable<MovieType>(KEY_TYPE)!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMovieOverviewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fragmentMovieOverviewRecyclerView.adapter = MovieGridAdapter(
            MovieGridAdapter.OnClickListener {
                viewModel.displayMovieDetails(it)
            }
        )

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner) {
            val movieId = it?.id ?: return@observe
            findNavController().navigate(MovieOverviewContainerFragmentDirections.actionShowMovieDetail(movieId))
        }

        return binding.root
    }

    companion object {
        private const val KEY_TYPE = "type"

        fun newInstance(type: MovieType): MovieOverviewFragment {
            return MovieOverviewFragment().apply {
                arguments = bundleOf(KEY_TYPE to type)
            }
        }
    }
}
