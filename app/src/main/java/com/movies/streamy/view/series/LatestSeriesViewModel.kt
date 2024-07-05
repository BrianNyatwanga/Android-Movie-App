package com.series.streamy.view.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.movies.streamy.R
import com.movies.streamy.di.IoDispatcher
import com.movies.streamy.model.dataSource.network.data.response.SeriesLatest
import com.movies.streamy.model.repository.implementation.SeriesRepositoryImpl
import com.movies.streamy.utils.AppUtil
import com.movies.streamy.view.series.SeriesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestSeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepositoryImpl,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _SeriesLatest = MutableLiveData<List<SeriesLatest?>?>()
    val SeriesLatest: LiveData<List<SeriesLatest?>?>
        get() = _SeriesLatest

    private val _viewState = MutableLiveData<SeriesViewState>()
    val viewState: LiveData<SeriesViewState>
        get() = _viewState

    fun getLatestSeries() {
        _viewState.postValue(SeriesViewState.Loading)
        viewModelScope.launch(iODispatcher) {
            val result = executeWithRetry(times = 3) {
                seriesRepository.getLatestSeries()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(SeriesViewState.Success)
                    val data = result.body
                    _SeriesLatest.postValue(data.results)
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
}
