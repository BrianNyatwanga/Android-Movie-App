package com.movies.streamy.view.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.movies.streamy.R
import com.movies.streamy.di.IoDispatcher
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesId
import com.movies.streamy.model.repository.implementation.SeriesRepositoryImpl
import com.movies.streamy.utils.AppUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepositoryImpl,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _TVseriesIds = MutableLiveData<List<TvSeriesId>>()
    val TVseriesIds: LiveData<List<TvSeriesId>>
        get() = _TVseriesIds

    private val _viewState = MutableLiveData<SeriesViewState>()
    val viewState: LiveData<SeriesViewState>
        get() = _viewState

    fun getSeriesIds() {
        _viewState.postValue(SeriesViewState.Loading)
        viewModelScope.launch(iODispatcher) {
            val result = executeWithRetry(times = 3) {
                seriesRepository.getTvSeriesIds()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(SeriesViewState.Success)
                    val data = result.body
                    _TVseriesIds.postValue(data.results)
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
                                    AppUtil.getErrorResponse(result.body as? ErrorResponse),
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
