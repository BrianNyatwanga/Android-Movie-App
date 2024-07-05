package com.movies.streamy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.movies.streamy.databinding.FragmentWatchlistBinding
import com.movies.streamy.viewmodel.WatchlistViewModel

class WatchlistFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
    private val viewModel: WatchlistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using Data Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watchlist, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Setup click listeners for genre, favorite, recent, tv series buttons
        setupClickListener()

        return binding.root
    }

    private fun setupClickListener() {
        binding.btnGenre.setOnClickListener {
            // Handle genre button click
            viewModel.loadGenreDetails()
        }

        binding.btnFavorite.setOnClickListener {
            // Handle favorite button click
            viewModel.loadFavoriteDetails()
        }

        binding.btnRecent.setOnClickListener {
            // Handle recent button click
            viewModel.loadRecentDetails()
        }

        binding.btnTvSeries.setOnClickListener {
            // Handle TV series button click
            viewModel.loadTvSeriesDetails()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WatchlistFragment().apply {
                arguments = Bundle().apply {
                    val ARG_PARAM1 = null
                    putString(ARG_PARAM1, param1)
                    val ARG_PARAM2 =null
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
