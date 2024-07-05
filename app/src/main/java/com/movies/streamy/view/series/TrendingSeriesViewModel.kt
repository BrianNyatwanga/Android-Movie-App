package com.movies.streamy.view.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.movies.streamy.R
import com.movies.streamy.di.IoDispatcher
import com.movies.streamy.model.dataSource.network.data.response.SeriesTrending
import com.movies.streamy.model.repository.implementation.SeriesRepositoryImpl
import com.movies.streamy.utils.AppUtil
import com.movies.streamy.view.series.SeriesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingSeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _trendingSeries = MutableLiveData<List<SeriesTrending?>?>()
    val SeriesTrending: LiveData<List<SeriesTrending?>?>
        get() = _trendingSeries

    private val _viewState = MutableLiveData<SeriesViewState>()
    val viewState: LiveData<SeriesViewState>
        get() = _viewState

    fun getTrendingSeries() {
        _viewState.postValue(SeriesViewState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val result = executeWithRetry(times = 3) {
                seriesRepository.getTrendingSeries()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(SeriesViewState.Success)
                    val data = result.body
                    _trendingSeries.postValue(data.results)
                }

                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                            SeriesViewState.Error(
                                    null,
                                    R.string.network_error_msg,
                                    null
                            )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                            SeriesViewState.Error(
                                    AppUtil.getErrorResponse(result.body),
                                    null,
                                    null
                            )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                            SeriesViewState.Error(
                                    null,
                                    R.string.unknown_error_msg,
                                    null
                            )
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up resources if needed
    }
}
