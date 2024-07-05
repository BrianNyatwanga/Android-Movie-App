package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiInterface {

    //NOW PLAYING
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<NowPlayingMovieResponse>

    //TOP RATED
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<TopRatedMovieResponse>

    //MOVIE CHANGES
    @GET("movie/changes")
    suspend fun getMovieId(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MovieIdResponse, ErrorResponse>

    //    POPULAR
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<PopularMovieResponse>







    }
