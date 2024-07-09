package com.movies.streamy.view.series

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.streamy.databinding.FragmentTrendingSeriesBinding
import com.movies.streamy.model.dataSource.network.data.response.SeriesPopular
import com.movies.streamy.model.dataSource.network.data.response.SeriesTrending
import com.movies.streamy.view.series.Adapters.TrendingSeriesAdapter
import com.movies.streamy.view.series.seriesDetails.PopularSeriesDetailsFragment
import com.movies.streamy.view.series.seriesDetails.TrendingSeriesDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TrendingSeriesFragment : Fragment() {
    private lateinit var binding: FragmentTrendingSeriesBinding
    private lateinit var viewModel: TrendingSeriesViewModel

    private val TrendingSeriesAdapter = TrendingSeriesAdapter { data: SeriesTrending -> itemClicked(data) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrendingSeriesViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingSeriesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        setupAdapter()
        setUpObservers()
        viewModel.getTrendingSeries()
    }

//    udfsij;SC;HICSDAH;SFA;'D'

    private fun setupRecyclerView(seriesTrending:List<SeriesTrending?>?) {
        if (seriesTrending.isNullOrEmpty())
        //
        else {
            TrendingSeriesAdapter.submitList(seriesTrending)
            binding.seriesRecyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = TrendingSeriesAdapter
            }
        }
    }
    private fun setupAdapter() {
        binding.seriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
                reverseLayout = true
                stackFromEnd = true
            }
            setHasFixedSize(false)
            adapter = TrendingSeriesAdapter
        }
    }

    private fun setUpObservers() {
        viewModel.SeriesTrending.observe(viewLifecycleOwner, :: setupRecyclerView)
        // You can add more actions here if needed
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            onViewStateChanged(state)
        })
    }

    private fun onViewStateChanged(state: SeriesViewState) {
        when (state) {
            is SeriesViewState.Loading -> showShimmerEffect()
            is SeriesViewState.Success -> {
                hideShimmerEffect()
            }
            is SeriesViewState.Error -> {
                hideShimmerEffect()
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.seriesRecyclerView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.seriesRecyclerView.visibility = View.VISIBLE
    }

    private fun itemClicked(data: SeriesTrending) {
        val fragment = TrendingSeriesDetailsFragment.newInstance(data)
        binding.frame.visibility = View.VISIBLE
        binding.frameTwo.visibility = View.GONE
        val trasaction = childFragmentManager.beginTransaction().replace(binding.frame.id, fragment)
        trasaction.commit()
        // Handle item click
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.seriesRecyclerView.adapter = null
    }
}
