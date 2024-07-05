package com.movies.streamy.view.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.movies.streamy.view.favorite.movies.FavoriteMoviesFragment
import com.movies.streamy.view.favorite.series.FavoriteSeriesFragment


class FavoritePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMoviesFragment()
            1 -> FavoriteSeriesFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}