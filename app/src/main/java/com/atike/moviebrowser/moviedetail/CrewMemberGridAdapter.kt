/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atike.moviebrowser.databinding.GridCrewItemBinding
import com.atike.moviebrowser.network.MovieCrewMember

class CrewMemberGridAdapter : ListAdapter<MovieCrewMember, MovieCrewMemberViewHolder>(CrewDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCrewMemberViewHolder {
        return MovieCrewMemberViewHolder(GridCrewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieCrewMemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private object CrewDiffCallback : DiffUtil.ItemCallback<MovieCrewMember>() {
    override fun areItemsTheSame(oldItem: MovieCrewMember, newItem: MovieCrewMember): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieCrewMember, newItem: MovieCrewMember): Boolean {
        return oldItem == newItem
    }
}

class MovieCrewMemberViewHolder(private var binding: GridCrewItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieCrewMember: MovieCrewMember) {
        binding.movieCrewMember = movieCrewMember
        binding.executePendingBindings()
    }
}
