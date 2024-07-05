package com.movies.streamy.view.favorite.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.streamy.databinding.FragmentFavoriteMoviesBinding
import com.movies.streamy.room.favorites.FavMovieDB
import com.movies.streamy.room.favorites.FavMovieDBRepository
import com.movies.streamy.view.favorite.FavoriteMoviesAdapter
import com.movies.streamy.view.favorite.FavoriteViewState

class FavoriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteMoviesViewModel
    private lateinit var adapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initViews()

        val database = FavMovieDB.getDatabase(requireContext())
        val repository = FavMovieDBRepository(database.FavMovieDao())

        val factory = FavoriteMoviesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(FavoriteMoviesViewModel::class.java)

        adapter = FavoriteMoviesAdapter()
        binding.FavMoviesRec.layoutManager = LinearLayoutManager(context)
        binding.FavMoviesRec.adapter = adapter

        viewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
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
        binding.FavMoviesRec.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.FavMoviesRec.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
