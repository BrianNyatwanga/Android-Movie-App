package com.movies.streamy.view.home

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
import com.movies.streamy.model.dataSource.network.data.response.SeriesLatest
import com.movies.streamy.model.dataSource.network.data.response.SeriesPopular
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
import com.movies.streamy.model.dataSource.network.data.response.homeData.TrailerResult
import com.movies.streamy.model.repository.abstraction.IHomeRepository
import com.movies.streamy.model.repository.implementation.MoviesRepositoryImpl
import com.movies.streamy.model.repository.implementation.SeriesRepositoryImpl
import com.movies.streamy.utils.AppUtil
import com.movies.streamy.view.movies.MoviesViewState
import com.movies.streamy.view.series.SeriesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: IHomeRepository,
    private val trailerRepository: TrailerImpl,
    private val seriesRepository: SeriesRepositoryImpl,
    private val moviesRepository: MoviesRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _movieList = MutableLiveData<List<HomeResult?>?>()
    val movieList: LiveData<List<HomeResult?>?>
        get() = _movieList

    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    private val _trailerList = MutableLiveData<List<TrailerResult>>()
     val trailerList: LiveData<List<TrailerResult>>
        get() = _trailerList

    private val _trailerVisible = MutableLiveData(false)
    val trailerVisible: LiveData<Boolean> get() = _trailerVisible

    private val _SeriesLatest = MutableLiveData<List<SeriesLatest?>?>()
    val SeriesLatest: LiveData<List<SeriesLatest?>?>
        get() = _SeriesLatest

    private val _SeriesPopular = MutableLiveData<List<SeriesPopular?>?>()
    val SeriesPopular: LiveData<List<SeriesPopular?>?>
        get() = _SeriesPopular

    private val _popularMovie = MutableLiveData<List<PopularMovieResult?>?>()
    val popularMovie: LiveData<List<PopularMovieResult?>?> get() = _popularMovie

    private val _latestMovie = MutableLiveData<List<NowPlayingMovieResult?>?>()
    val latestMovie: LiveData<List<NowPlayingMovieResult?>?> get() = _latestMovie

    fun clearTrailerList() {
        _trailerList.value = emptyList()
    }

    fun setTrailerVisible(visible: Boolean) {
        _trailerVisible.value = visible
    }

    fun getMovieLists() {
        _viewState.postValue(HomeViewState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val result = executeWithRetry(times = 3) {
                homeRepository.getMovieLists()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(HomeViewState.Success)
                    val data = result.body
                    _movieList.postValue(data.results)
                }

                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
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
        viewModelScope.launch(ioDispatcher) {
            try {
//            clearTrailerList()
                val response = trailerRepository.getTrailerByMovieId(movieId)
                response?.let {
                    _trailerList.postValue(it.results)
                }
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun getPopularSeries() {
        _viewState.postValue(HomeViewState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val result = executeWithRetry(times = 3) {
                seriesRepository.getPopularSeries()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(HomeViewState.Success)
                    val data = result.body
                    _SeriesPopular.postValue(data.results)
                }
                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }
                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }
                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }
    fun getLatestSeries() {
        _viewState.postValue(HomeViewState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val result = executeWithRetry(times = 3) {
                seriesRepository.getLatestSeries()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(HomeViewState.Success)
                    val data = result.body
                    _SeriesLatest.postValue(data.results)
                }
                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }
                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }
                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getPopularMovies(){
        _viewState.postValue(HomeViewState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val result = executeWithRetry(times = 3){
                moviesRepository.getPopularMovies()
            }
            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(HomeViewState.Success)

                    val data = result.body

                    _popularMovie.postValue(data.results)
                }

                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }
    fun getLatestMovies(){
        _viewState.postValue(HomeViewState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val result = executeWithRetry(times = 3){
                moviesRepository.getNowPlayingMovies()
            }
            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(HomeViewState.Success)
                    val data = result.body
                    _latestMovie.postValue(data.results as List<NowPlayingMovieResult>?)
                }
                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }
}

