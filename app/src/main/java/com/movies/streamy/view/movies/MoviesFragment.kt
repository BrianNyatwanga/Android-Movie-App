package com.movies.streamy.view.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentMoviesBinding
import com.movies.streamy.view.movies.adapters.MoviesPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment: Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        setupViewPagerAndTabs()

        return binding.root
    }

    private fun setupViewPagerAndTabs() {
        val pagerAdapter = MoviesPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        // Link the TabLayout with the ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.now_playing)
                1 -> getString(R.string.top_rated)
                2 -> getString(R.string.popular)
                else -> null
            }
        }.attach()
    }
}
