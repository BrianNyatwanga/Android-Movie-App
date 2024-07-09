package com.movies.streamy.view.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.movies.streamy.R
import com.movies.streamy.di.IoDispatcher
import com.movies.streamy.model.dataSource.implementation.TrailerImpl
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResult
import com.movies.streamy.model.dataSource.network.data.response.homeData.TrailerResult
import com.movies.streamy.model.repository.implementation.MoviesRepositoryImpl
import com.movies.streamy.utils.AppUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val homeRepository: MoviesRepositoryImpl,
    private val trailerRepository : TrailerImpl,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _popularMovies = MutableLiveData<List<PopularMovieResult?>?>()
    val popularMovies: LiveData<List<PopularMovieResult?>?>
        get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<TopRatedMovieResult?>?>()
    val topRatedMovies: LiveData<List<TopRatedMovieResult?>?>
        get() = _topRatedMovies

    private val _nowPlayingMovies = MutableLiveData<List<NowPlayingMovieResult>>()
    val _nowPlayingMovies2 = MutableLiveData<List<NowPlayingMovieResult>>()
    val nowPlayingMovies: LiveData<List<NowPlayingMovieResult>>
        get() = _nowPlayingMovies

    private val _trailerList = MutableLiveData<List<TrailerResult>>()
    val trailerList: LiveData<List<TrailerResult>>
        get() = _trailerList

    private val _viewState = MutableLiveData<MoviesViewState>()
    val viewState: LiveData<MoviesViewState>
        get() = _viewState
    private val _trailerVisible = MutableLiveData(false)
    val trailerVisible: LiveData<Boolean>
        get() = _trailerVisible


    init {
        getNowPlayingMovies()
        getNowPlayingMovies2()
        getTopRatedMovies()
        getPopularMovies()
    }

    fun setTrailerVisible(visible: Boolean) {
        _trailerVisible.value = visible
    }


    fun getPopularMovies(){
        _viewState.postValue(MoviesViewState.Loading)
        viewModelScope.launch(iODispatcher) {
            val result = executeWithRetry(times = 3){
                homeRepository.getPopularMovies()
            }
            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(MoviesViewState.Success)

                    val data = result.body

                    _popularMovies.postValue(data.results)
                }

                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getNowPlayingMovies(){
        _viewState.postValue(MoviesViewState.Loading)
        viewModelScope.launch(iODispatcher) {
            val result = executeWithRetry(times = 3){
                homeRepository.getNowPlayingMovies()
            }
            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(MoviesViewState.Success)
                    val data = result.body
//                    _popularMovies.postValue(data.results)
                    _nowPlayingMovies.postValue(data.results as List<NowPlayingMovieResult>?)
                }
                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getNowPlayingMovies2(){
        _viewState.postValue(MoviesViewState.Loading)
        viewModelScope.launch(iODispatcher) {
            val result = executeWithRetry(times = 3){
                homeRepository.getNowPlayingMovies2()
            }
            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(MoviesViewState.Success)
                    val data = result.body

                    _nowPlayingMovies.postValue(data.results as List<NowPlayingMovieResult>?)
                }
                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getTopRatedMovies(){
        _viewState.postValue(MoviesViewState.Loading)
        viewModelScope.launch(iODispatcher) {
            val result = executeWithRetry(times = 3){
                homeRepository.getTopRatedMovies()
            }
            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(MoviesViewState.Success)

                    val data = result.body

                    _topRatedMovies.postValue(data.results)
                }

                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        MoviesViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }
    fun getTrailerByMovieId(movieId: Int) {
        viewModelScope.launch(iODispatcher) {
            try {
                val response = trailerRepository.getTrailerByMovieId(movieId)
                response?.let {
                    _trailerList.postValue(it.results)
                }
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}
