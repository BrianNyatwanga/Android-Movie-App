package com.movies.streamy.view.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.streamy.R
import com.movies.streamy.di.IoDispatcher
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResult
import com.movies.streamy.model.repository.implementation.MoviesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val homeRepository: MoviesRepositoryImpl,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _popularMovies = MutableLiveData<List<PopularMovieResult?>?>()
    val popularMovies: LiveData<List<PopularMovieResult?>?>
        get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<TopRatedMovieResult?>?>()
    val topRatedMovies: LiveData<List<TopRatedMovieResult?>?>
        get() = _topRatedMovies

    private val _nowPlayingMovies = MutableLiveData<List<NowPlayingMovieResult?>?>()
    val nowPlayingMovies: LiveData<List<NowPlayingMovieResult?>?>
        get() = _nowPlayingMovies

    private val _viewState = MutableLiveData<MoviesViewState>()
    val viewState: LiveData<MoviesViewState>
        get() = _viewState

    init {
        getNowPlayingMovies()
        getTopRatedMovies()
        getPopularMovies()
    }

    fun getPopularMovies() {
        _viewState.postValue(MoviesViewState.Loading)
        viewModelScope.launch {
            while(isActive){
                delay(10000L)
                val response = homeRepository.getPopularMovies()
                if (response.isSuccessful){
                    _viewState.postValue(MoviesViewState.Success)
                    _popularMovies.postValue(response.body()!!.results)
                }else{
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

            }
//            val result = executeWithRetry(times = 3) {
//            }
//
//            when (result) {
//                is NetworkResponse.Success -> {
//                    _viewState.postValue(MoviesViewState.Success)
//
//                    val data = result.body
//
//                    _popularMovies.postValue(data)
//                }
//
//                is NetworkResponse.NetworkError -> {
//                    _viewState.postValue(
//                        MoviesViewState.Error(
//                            null,
//                            R.string.network_error_msg,
//                            null
//                        )
//                    )
//                }
//
//                is NetworkResponse.ServerError -> {
//                    _viewState.postValue(
//                        MoviesViewState.Error(
//                            AppUtil.getErrorResponse(result.body),
//                            null,
//                            null
//                        )
//                    )
//                }
//
//                is NetworkResponse.UnknownError -> {
//                    _viewState.postValue(
//                        MoviesViewState.Error(
//                            null,
//                            R.string.unknown_error_msg,
//                            null
//                        )
//                    )
//                }
//            }
        }
    }
    fun getNowPlayingMovies() {
        _viewState.postValue(MoviesViewState.Loading)
        viewModelScope.launch {
            while(isActive){
                delay(10000L)
                val response = homeRepository.getNowPlayingMovies()
                if (response.isSuccessful){
                    _viewState.postValue(MoviesViewState.Success)
                    _nowPlayingMovies.postValue(response.body()!!.results)
                }else{
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

            }}}
    fun getTopRatedMovies() {
        _viewState.postValue(MoviesViewState.Loading)
        viewModelScope.launch {
            while(isActive){
                delay(10000L)
                val response = homeRepository.getTopRatedMovies()
                if (response.isSuccessful){
                    _viewState.postValue(MoviesViewState.Success)
                    _topRatedMovies.postValue(response.body()!!.results)
                }else{
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

            }}}

    fun getMovieDetails() {

    }
}
