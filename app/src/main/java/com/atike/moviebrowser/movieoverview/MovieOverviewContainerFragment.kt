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
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.atike.moviebrowser.R
import com.atike.moviebrowser.databinding.FragmentMovieOverviewContainerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MovieOverviewContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieOverviewContainerBinding.inflate(inflater, container, false)
        setUpTabLayoutWithViewPager(binding.fragmentOverviewContainerTabLayout, binding.fragmentOverviewViewPager)
        return binding.root
    }

    private fun setUpTabLayoutWithViewPager(tabLayout: TabLayout, viewPager: ViewPager2) {
        viewPager.adapter = OverviewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(
                when (position) {
                    0 -> R.string.upcoming
                    1 -> R.string.now_playing
                    2 -> R.string.popular
                    else -> R.string.top_rated
                }
            )
        }.attach()
    }
}

private class OverviewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return MovieOverviewFragment.newInstance(
            when (position) {
                0 -> MovieType.UPCOMING
                1 -> MovieType.NOW_PLAYING
                2 -> MovieType.POPULAR
                else -> MovieType.TOP_RATED
            }
        )
    }
}
