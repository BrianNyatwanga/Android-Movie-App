package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.PlayTrailerResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.TrailerResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiInterface {
    @GET("trending/all/day")
    suspend fun getMovieList(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<HomeTrendingResponse, ErrorResponse>





}
