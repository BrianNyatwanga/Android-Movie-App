package com.movies.streamy.view.favorite.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.movies.streamy.room.favorites.FavMovieDBRepository
import com.movies.streamy.room.favorites.FavMovieEntity

class FavoriteMoviesViewModel(private val repository: FavMovieDBRepository) : ViewModel() {
    val favoriteMovies: LiveData<List<FavMovieEntity>> = liveData {
        emitSource(repository.getFavoriteMovies())
    }
}

class FavoriteMoviesViewModelFactory(private val repository: FavMovieDBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteMoviesViewModel(repository) as T
    }
}


