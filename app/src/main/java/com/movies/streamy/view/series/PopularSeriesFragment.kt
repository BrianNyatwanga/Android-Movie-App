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
import com.movies.streamy.databinding.FragmentPopularSeriesBinding
import com.movies.streamy.model.dataSource.network.data.response.SeriesLatest
import com.movies.streamy.model.dataSource.network.data.response.SeriesPopular
import com.movies.streamy.view.series.Adapters.PopularSeriesAdapter
import com.movies.streamy.view.series.SeriesViewState
import com.movies.streamy.view.series.seriesDetails.LatestSeriesDetailsFragment
import com.movies.streamy.view.series.seriesDetails.PopularSeriesDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularSeriesFragment : Fragment() {
    private lateinit var binding: FragmentPopularSeriesBinding
    private lateinit var viewModel: PopularSeriesViewModel

    private val popularSeriesAdapter = PopularSeriesAdapter { series -> itemClicked(series) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PopularSeriesViewModel::class.java)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularSeriesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setUpObservers()
        viewModel.getPopularSeries()
    }

    private fun setupRecyclerView(seriesPopular:List<SeriesPopular?>?) {
        if (seriesPopular.isNullOrEmpty())
        //
        else {
            popularSeriesAdapter.submitList(seriesPopular)
            binding.series1.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = popularSeriesAdapter
            }
        }
    }
    private fun setUpObservers() {
        viewModel.SeriesPopular.observe(viewLifecycleOwner, :: setupRecyclerView)
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
                //bind recycler view data
                binding.series1

            }
            is SeriesViewState.Error -> {
                hideShimmerEffect()
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.series1.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.series1.visibility = View.VISIBLE
    }


    private fun itemClicked(data: SeriesPopular) {
        val fragment = PopularSeriesDetailsFragment.newInstance(data)
        binding.frame.visibility = View.VISIBLE
        binding.frameTwo.visibility = View.GONE
        val trasaction = childFragmentManager.beginTransaction().replace(binding.frame.id, fragment)
        trasaction.commit()
    }
}
