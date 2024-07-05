package com.movies.streamy.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.movies.streamy.databinding.FragmentFavoriteBinding
import com.movies.streamy.room.favorites.FavMovieDB
import com.movies.streamy.room.favorites.FavMovieDBRepository
import com.movies.streamy.view.favorite.movies.FavoriteMoviesViewModel
import com.movies.streamy.view.favorite.movies.FavoriteMoviesViewModelFactory


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesViewModel: FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FavMovieDB.getDatabase(requireContext())
        val repository = FavMovieDBRepository(database.FavMovieDao())

        val moviesFactory = FavoriteMoviesViewModelFactory(repository)

        moviesViewModel = ViewModelProvider(this, moviesFactory).get(FavoriteMoviesViewModel::class.java)

        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        val pagerAdapter = FavoritePagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Movie"
                1 -> tab.text = "Series"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
