package com.movies.streamy.view.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.movies.streamy.databinding.FragmentSeriesBinding
import com.movies.streamy.view.series.Adapters.SeriesPagerAdapter

class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
//    private val viewModel: SeriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
//        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the ViewPager with the SeriesPagerAdapter
        val adapter = SeriesPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // Connect the TabLayout with the ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Latest"
                1 -> "Trending"
                2 -> "Popular"
                else -> "Latest"
            }
        }.attach()
    }
}
