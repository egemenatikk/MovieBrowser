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
import com.atike.moviebrowser.databinding.GridCastItemBinding
import com.atike.moviebrowser.network.MovieCastMember

class CastMemberGridAdapter : ListAdapter<MovieCastMember, MovieCastMemberViewHolder>(CastDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastMemberViewHolder {
        return MovieCastMemberViewHolder(GridCastItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieCastMemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private object CastDiffCallback : DiffUtil.ItemCallback<MovieCastMember>() {
    override fun areItemsTheSame(oldItem: MovieCastMember, newItem: MovieCastMember): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MovieCastMember, newItem: MovieCastMember): Boolean {
        return oldItem == newItem
    }
}

class MovieCastMemberViewHolder(private var binding: GridCastItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieCastMember: MovieCastMember) {
        binding.movieCastMember = movieCastMember
        binding.executePendingBindings()
    }
}
