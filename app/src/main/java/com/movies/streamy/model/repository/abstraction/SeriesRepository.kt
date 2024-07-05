package com.movies.streamy.model.repository.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesLatestResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesPopularResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.TvseriesResponse

interface SeriesRepository {
    suspend fun getTvSeriesIds(): NetworkResponse<TvseriesResponse, ErrorResponse>
    suspend fun getPopularSeries(): NetworkResponse<TvSeriesPopularResponse, ErrorResponse>
    suspend fun getTrendingSeries(): NetworkResponse<TvSeriesTrendingResponse, ErrorResponse>
    suspend fun getLatestSeries(): NetworkResponse<TvSeriesLatestResponse, ErrorResponse>



}