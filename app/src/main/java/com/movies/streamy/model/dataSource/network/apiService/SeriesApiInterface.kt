package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesLatestResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesPopularResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.TvseriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesApiInterface {
    @GET("genre/tv/list")
    suspend fun getTvSeriesId(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<TvseriesResponse, ErrorResponse>

     @GET("tv/top_rated")
    suspend fun getTrendingSeries(
    @Query("page") page: Int = 1,
    @Query("language") language: String = "en-US"
    ): NetworkResponse<TvSeriesTrendingResponse, ErrorResponse>

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<TvSeriesPopularResponse, ErrorResponse>
    @GET("tv/airing_today")
    suspend fun getLatestSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<TvSeriesLatestResponse, ErrorResponse>
}
