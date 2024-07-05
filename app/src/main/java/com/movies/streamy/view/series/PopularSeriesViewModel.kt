package com.movies.streamy.view.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.movies.streamy.R
import com.movies.streamy.di.IoDispatcher
import com.movies.streamy.model.dataSource.network.data.response.SeriesPopular
import com.movies.streamy.model.repository.implementation.SeriesRepositoryImpl
import com.movies.streamy.utils.AppUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularSeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepositoryImpl,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _SeriesPopular = MutableLiveData<List<SeriesPopular?>?>()
    val SeriesPopular: LiveData<List<SeriesPopular?>?>
        get() = _SeriesPopular

    private val _viewState = MutableLiveData<SeriesViewState>()
    val viewState: LiveData<SeriesViewState>
        get() = _viewState
    fun getPopularSeries() {
        _viewState.postValue(SeriesViewState.Loading)
        viewModelScope.launch(iODispatcher) {
            val result = executeWithRetry(times = 3) {
                seriesRepository.getPopularSeries()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(SeriesViewState.Success)
                    val data = result.body
                    _SeriesPopular.postValue(data.results)
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


//    fun getPopularSeries() {
//        _viewState.postValue(SeriesViewState.Loading)
//        viewModelScope.launch(ioDispatcher) {
//            val result = executeWithRetry(times = 3) {
//                seriesRepository.getPopularSeries()
//            }
//
//            when (result) {
//                is NetworkResponse.Success -> {
//                    _SeriesPopular.postValue(ViewState.Success(result.body))
//                }
//                is NetworkResponse.NetworkError -> {
//                    _SeriesPopular.postValue(
//                            ViewState.Error(
//                                    null,
//                                    R.string.network_error_msg,
//                                    null
//                            )
//                    )
//                }
//                is NetworkResponse.ServerError -> {
//                    _SeriesPopular.postValue(
//                            ViewState.Error(
//                                    AppUtil.getErrorResponse(result.body),
//                                    null,
//                                    null
//                            )
//                    )
//                }
//                is NetworkResponse.UnknownError -> {
//                    _SeriesPopular.postValue(
//                            ViewState.Error(
//                                    null,
//                                    R.string.unknown_error_msg,
//                                    null
//                            )
//                    )
//                }
//            }
//        }
//    }
//
//    sealed class ViewState {
//        object Loading : ViewState()
//        data class Success(val seriesList: TvSeriesPopularResponse) : ViewState()
//        data class Error(val message: String?, val errorMsgRes: Int?, val error: Throwable?) : ViewState()
//    }
//}
