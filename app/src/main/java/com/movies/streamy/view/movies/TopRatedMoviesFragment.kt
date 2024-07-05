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
import com.movies.streamy.databinding.FragmentMoviesTopRatedBinding
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResult
import com.movies.streamy.utils.Prefs
import com.movies.streamy.utils.observe
import com.movies.streamy.view.moviedetails.TopRatedMovieDetailsFragment
import com.movies.streamy.view.movies.adapters.TopRatedMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TAG = "topratedmovies"
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesTopRatedBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var prefs: Prefs

    private val topRatedMovieAdapter =
        TopRatedMovieAdapter{
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_top_rated, container, false)
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        //binding.viewModel = viewModel
        //binding.lifecycleOwner = viewLifecycleOwner


            binding.rvTopRatedMovies.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                setHasFixedSize(false)
                adapter = topRatedMovieAdapter
            }
            binding.frameOne.visibility = View.GONE
            viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer{
            Log.d(TAG, it.toString())
            topRatedMovieAdapter.asyncList.submitList(it)

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
        binding.rvTopRatedMovies.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvTopRatedMovies.visibility = View.VISIBLE
    }

    private fun itemClicked(data: TopRatedMovieResult) {
        val fragment = TopRatedMovieDetailsFragment.newInstance(data)
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