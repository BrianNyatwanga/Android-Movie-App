package com.movies.streamy.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.streamy.room.favorites.FavMovieDBRepository
import com.movies.streamy.room.favorites.FavMovieEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavMovieDBRepository) : ViewModel() {

    val favoriteMovies: LiveData<List<FavMovieEntity>> = repository.getFavoriteMovies()
    val favoriteSeries: LiveData<List<FavMovieEntity>> = repository.getFavoriteSeries()

    fun refreshFavorites() {
        viewModelScope.launch {
            repository.getFavoriteMovies()
            repository.getFavoriteSeries()
        }
    }
}