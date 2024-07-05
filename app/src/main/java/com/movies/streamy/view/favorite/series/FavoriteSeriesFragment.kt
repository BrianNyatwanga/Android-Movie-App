package com.movies.streamy.view.favorite.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.streamy.databinding.FragmentFavoriteSeriesBinding
import com.movies.streamy.room.favorites.FavMovieDB
import com.movies.streamy.room.favorites.FavMovieDBRepository
import com.movies.streamy.view.favorite.FavoriteViewState
import com.movies.streamy.view.favorite.FavoriteMoviesAdapter


class FavoriteSeriesFragment : Fragment() {

    private var _binding: FragmentFavoriteSeriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteSeriesViewModel
    private lateinit var adapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FavMovieDB.getDatabase(requireContext())
        val repository = FavMovieDBRepository(database.FavMovieDao())

        val factory = FavoriteSeriesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(FavoriteSeriesViewModel::class.java)

        adapter = FavoriteMoviesAdapter()
        binding.FavSeriesRec.layoutManager = LinearLayoutManager(context)
        binding.FavSeriesRec.adapter = adapter

        viewModel.favoriteSeries.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }
    }
    private fun onViewStateChanged(state: FavoriteViewState) {
        hideShimmerEffect()
        when (state) {
            is FavoriteViewState.Loading -> {
                showShimmerEffect()
            }
            is FavoriteViewState.Success -> {
                hideShimmerEffect()
            }
            is FavoriteViewState.Error -> {
                hideShimmerEffect()
                // Handle error state
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.FavSeriesRec.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.FavSeriesRec.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
