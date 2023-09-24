/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.moviedetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atike.moviebrowser.databinding.RecommendedAndSimilarMovieViewItemBinding
import com.atike.moviebrowser.network.Movie

class RecommendedAndSimilarMovieGridAdapter : ListAdapter<Movie,
        RecommendedAndSimilarMovieViewHolder>(RecommendedAndSimilarMovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedAndSimilarMovieViewHolder {
        return RecommendedAndSimilarMovieViewHolder(
            RecommendedAndSimilarMovieViewItemBinding
                .inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: RecommendedAndSimilarMovieViewHolder, position: Int) {
        val recommendedAndSimilarMovie = getItem(position)
        holder.bind(recommendedAndSimilarMovie)
    }

}

private object RecommendedAndSimilarMovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem === newItem
    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}

class RecommendedAndSimilarMovieViewHolder(
    private val binding: RecommendedAndSimilarMovieViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recommendedAndSimilarMovie: Movie) {
        binding.recommendedAndSimilarMovie = recommendedAndSimilarMovie
        binding.executePendingBindings()
    }
}
