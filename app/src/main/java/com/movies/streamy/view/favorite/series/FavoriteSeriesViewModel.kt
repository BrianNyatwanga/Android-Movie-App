package com.movies.streamy.view.favorite.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.movies.streamy.room.favorites.FavMovieDBRepository
import com.movies.streamy.room.favorites.FavMovieEntity

class FavoriteSeriesViewModel(private val repository: FavMovieDBRepository) : ViewModel() {
    val favoriteSeries: LiveData<List<FavMovieEntity>> = liveData {
        emitSource(repository.getFavoriteSeries())
    }
}

class FavoriteSeriesViewModelFactory(private val repository: FavMovieDBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteSeriesViewModel(repository) as T
    }
}
