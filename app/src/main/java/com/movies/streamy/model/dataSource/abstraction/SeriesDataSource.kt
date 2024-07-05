package com.movies.streamy.model.dataSource.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesLatestResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesPopularResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.TvseriesResponse

interface SeriesDataSource {
    suspend fun getTvSeriesIds(): NetworkResponse<TvseriesResponse, ErrorResponse>
    suspend fun getPopularSeriesIds(): NetworkResponse<TvSeriesPopularResponse, ErrorResponse>
    suspend fun getTrendingSeriesIds(): NetworkResponse<TvSeriesTrendingResponse, ErrorResponse>
    suspend fun getLatestSeriesIds(): NetworkResponse<TvSeriesLatestResponse, ErrorResponse>
}






