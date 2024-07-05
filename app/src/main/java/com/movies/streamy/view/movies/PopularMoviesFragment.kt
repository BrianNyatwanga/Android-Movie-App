package com.movies.streamy.view.movies

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentMoviesPopularBinding
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import com.movies.streamy.utils.Prefs
import com.movies.streamy.utils.observe
import com.movies.streamy.view.moviedetails.PopularMovieDetailsFragment
import com.movies.streamy.view.movies.adapters.PopularMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TAG = "popularmovies"

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesPopularBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var prefs: Prefs

    private val popularMovieAdapter =
        PopularMovieAdapter{
            movie -> itemClicked(movie)
        }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_popular, container, false)

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        showShimmerEffect()

        binding.rvPopularMovies.apply {
            hideShimmerEffect()
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(false)
            adapter = popularMovieAdapter
        }

        binding.frameOne.visibility = View.GONE

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer{
            Log.d(TAG, it.toString())
            popularMovieAdapter.asyncList.submitList(it)


        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        showShimmerEffect()
        setUpObservers()
    }

    private fun setUpObservers() {
        observe(viewModel.viewState, ::onViewStateChanged)
    }

    private fun onViewStateChanged(state: MoviesViewState) {
        hideShimmerEffect()
        when (state) {
            is MoviesViewState.Loading -> {
                showShimmerEffect()
            }

            is MoviesViewState.Success -> {
                hideShimmerEffect()
            }

            is MoviesViewState.Error -> {
                hideShimmerEffect()
//                 showSnackBar(state.errorMessage, false)
            }

            else -> {}
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.rvPopularMovies.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvPopularMovies.visibility = View.VISIBLE
    }

    private fun itemClicked(data: PopularMovieResult) {
        val fragment = PopularMovieDetailsFragment.newInstance(data)
        binding.frameOne.visibility = View.VISIBLE
        binding.frameTwo.visibility = View.GONE
        val tras = childFragmentManager.beginTransaction().replace(binding.frameOne.id, fragment)
        tras.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}