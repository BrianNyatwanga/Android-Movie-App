package com.movies.streamy.view.series

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentLatestSeriesBinding
import com.movies.streamy.model.dataSource.network.data.response.SeriesLatest
import com.movies.streamy.utils.Prefs
import com.movies.streamy.view.series.Adapters.LatestSeriesAdapter
import com.movies.streamy.view.series.SeriesViewState
import com.series.streamy.view.series.LatestSeriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LatestSeriesFragment : Fragment() {
    private lateinit var binding: FragmentLatestSeriesBinding
    private lateinit var viewModel: LatestSeriesViewModel
    private lateinit var prefs: Prefs

    private val latestSeriesAdapter =
        LatestSeriesAdapter { data: SeriesLatest -> itemClicked(data) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LatestSeriesViewModel::class.java)
        prefs = Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_latest_series, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        showShimmerEffect()
        setUpObservers()
        viewModel.getLatestSeries()
        setUpAdapter()

    }

    private fun setupRecyclerView(seriesLatest:List<SeriesLatest?>?) {
        if (seriesLatest.isNullOrEmpty())
        //
        else {
            latestSeriesAdapter.submitList(seriesLatest)
            binding.rvSeries.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = latestSeriesAdapter
            }
        }
    }

    private fun setUpObservers() {
        viewModel.SeriesLatest.observe(viewLifecycleOwner, :: setupRecyclerView)
            // You can add more actions here if needed
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            onViewStateChanged(state)
        })
    }

    private fun setUpAdapter() {
        binding.rvSeries.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
                reverseLayout = true
                stackFromEnd = true
            }
            setHasFixedSize(false)
            adapter = latestSeriesAdapter
        }
    }


    private fun onViewStateChanged(state: SeriesViewState) {
        when (state) {
            is SeriesViewState.Loading -> {
                showShimmerEffect()
            }
            is SeriesViewState.Success -> {
                hideShimmerEffect()
            }
            is SeriesViewState.Error -> {
                hideShimmerEffect()
                // Handle error state
            }
            else -> {}
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.rvSeries.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvSeries.visibility = View.VISIBLE
    }

    private fun itemClicked(data: SeriesLatest) {
        // Handle item click
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSeries.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
