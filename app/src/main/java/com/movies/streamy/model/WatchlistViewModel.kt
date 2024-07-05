package com.movies.streamy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movies.streamy.model.Movie

class WatchlistViewModel : ViewModel() {

    private val _selectedMovieDetails = MutableLiveData<Movie>()
    val selectedMovieDetails: LiveData<Movie>
        get() = _selectedMovieDetails

    fun loadGenreDetails() {
        // Simulate loading details for genre
        val genreDetails = Movie("Genre Movie", "Genre Movie Description")
        _selectedMovieDetails.postValue(genreDetails)
    }

    fun loadFavoriteDetails() {
        // Simulate loading details for favorite movie
        val favoriteDetails = Movie("Favorite Movie", "Favorite Movie Description")
        _selectedMovieDetails.postValue(favoriteDetails)
    }

    fun loadRecentDetails() {
        // Simulate loading details for recent movie
        val recentDetails = Movie("Recent Movie", "Recent Movie Description")
        _selectedMovieDetails.postValue(recentDetails)
    }

    fun loadTvSeriesDetails() {
        // Simulate loading details for TV series
        val tvSeriesDetails = Movie("TV Series", "TV Series Description")
        _selectedMovieDetails.postValue(tvSeriesDetails)
    }
}
