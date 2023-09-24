/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atike.moviebrowser.moviedetail.CastMemberGridAdapter
import com.atike.moviebrowser.moviedetail.CrewMemberGridAdapter
import com.atike.moviebrowser.moviedetail.RecommendedAndSimilarMovieGridAdapter
import com.atike.moviebrowser.movieoverview.MovieGridAdapter
import com.atike.moviebrowser.network.Movie
import com.atike.moviebrowser.network.MovieCastMember
import com.atike.moviebrowser.network.MovieCrewMember

@BindingAdapter("movieList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    (recyclerView.adapter as? MovieGridAdapter)?.submitList(data.orEmpty())
}

@BindingAdapter("castList")
fun bindCastRecyclerView(recyclerView: RecyclerView, data: List<MovieCastMember>?) {
    (recyclerView.adapter as? CastMemberGridAdapter)?.submitList(data.orEmpty())
}

@BindingAdapter("crewList")
fun bindCrewRecyclerView(recyclerView: RecyclerView, data: List<MovieCrewMember>?) {
    (recyclerView.adapter as? CrewMemberGridAdapter)?.submitList(data.orEmpty())
}

@BindingAdapter("recommendedAndSimilarMovieList")
fun bindRecommendedAndSimilarMoviesRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    (recyclerView.adapter as? RecommendedAndSimilarMovieGridAdapter)?.submitList(data.orEmpty())
}
