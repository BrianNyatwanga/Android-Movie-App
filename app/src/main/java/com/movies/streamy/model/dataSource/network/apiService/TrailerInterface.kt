package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.PlayTrailerResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.TrailerResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface TrailerInterface {
    @GET("movie/{movie_id}/videos")
    suspend fun playTrailer(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = "en-US",
    ): NetworkResponse<PlayTrailerResponse, ErrorResponse>
}