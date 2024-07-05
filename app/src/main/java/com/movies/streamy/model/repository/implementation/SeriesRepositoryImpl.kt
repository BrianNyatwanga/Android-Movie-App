package com.movies.streamy.model.repository.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.SeriesDataSource
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesLatestResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesPopularResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.TvseriesResponse
import com.movies.streamy.model.repository.abstraction.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesDataSource: SeriesDataSource
// Inject SeriesDataSource
) : SeriesRepository {

    override suspend fun getTvSeriesIds(): NetworkResponse<TvseriesResponse, ErrorResponse> {
        return seriesDataSource.getTvSeriesIds()
    }

    override suspend fun getPopularSeries(): NetworkResponse<TvSeriesPopularResponse, ErrorResponse> {
        return seriesDataSource.getPopularSeriesIds()
    }

    override suspend fun getTrendingSeries(): NetworkResponse<TvSeriesTrendingResponse, ErrorResponse> {
        return seriesDataSource.getTrendingSeriesIds()
    }

    override suspend fun getLatestSeries(): NetworkResponse<TvSeriesLatestResponse, ErrorResponse> {
        return seriesDataSource.getLatestSeriesIds()
    }
}
