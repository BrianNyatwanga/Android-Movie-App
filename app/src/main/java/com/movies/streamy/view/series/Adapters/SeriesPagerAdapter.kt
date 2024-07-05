package com.movies.streamy.view.series.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.movies.streamy.view.series.LatestSeriesFragment
import com.movies.streamy.view.series.PopularSeriesFragment
import com.movies.streamy.view.series.TrendingSeriesFragment


class SeriesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LatestSeriesFragment()
            1 -> TrendingSeriesFragment()
            2 -> PopularSeriesFragment()
            else -> LatestSeriesFragment()
        }
    }
}
