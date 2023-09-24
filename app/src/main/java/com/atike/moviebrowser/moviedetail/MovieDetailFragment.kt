/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.atike.moviebrowser.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory(
            MovieDetailFragmentArgs.fromBundle(requireArguments()).movieId
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMovieDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fragmentDetailCastMembersRecyclerView.adapter = CastMemberGridAdapter()
        binding.fragmentDetailCrewMembersRecyclerView.adapter = CrewMemberGridAdapter()
        binding.fragmentDetailRecommendedMovies.adapter = RecommendedAndSimilarMovieGridAdapter()
        binding.fragmentDetailSimilarMovies.adapter = RecommendedAndSimilarMovieGridAdapter()
        return binding.root
    }
}
