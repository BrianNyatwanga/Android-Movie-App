package com.movies.streamy.model.dataSource.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.BuildConfig
import com.movies.streamy.model.dataSource.abstraction.SeriesDataSource
import com.movies.streamy.model.dataSource.network.apiService.SeriesApiInterface
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesLatestResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesPopularResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.TvseriesResponse

import javax.inject.Inject

class SeriesDataSourceImpl @Inject constructor(
    private val seriesApiInterface: SeriesApiInterface // Single injection of the API interface
) : SeriesDataSource {

    override suspend fun getTvSeriesIds(): NetworkResponse<TvseriesResponse, ErrorResponse> {
        return seriesApiInterface.getTvSeriesId()
    }

    override suspend fun getPopularSeriesIds(): NetworkResponse<TvSeriesPopularResponse, ErrorResponse> {
        return seriesApiInterface.getPopularSeries()
    }

    override suspend fun getTrendingSeriesIds(): NetworkResponse<TvSeriesTrendingResponse, ErrorResponse> {
        return seriesApiInterface.getTrendingSeries()
    }

    override suspend fun getLatestSeriesIds(): NetworkResponse<TvSeriesLatestResponse, ErrorResponse> {
        return seriesApiInterface.getLatestSeries()
    }
}
