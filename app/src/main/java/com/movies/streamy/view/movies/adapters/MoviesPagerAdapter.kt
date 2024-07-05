package com.movies.streamy.view.movies.adapters


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.movies.streamy.view.movies.NowPlayingMoviesFragment
import com.movies.streamy.view.movies.PopularMoviesFragment
import com.movies.streamy.view.movies.TopRatedMoviesFragment

class MoviesPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {NowPlayingMoviesFragment()}
            1 -> {TopRatedMoviesFragment()}
            else -> PopularMoviesFragment()
        }
    }
}