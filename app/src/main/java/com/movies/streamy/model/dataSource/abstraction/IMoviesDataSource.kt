package com.movies.streamy.model.dataSource.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResponse
import retrofit2.Response

interface IMoviesDataSource {
    suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse>

    suspend fun getPopularMovies(): Response<PopularMovieResponse>
    suspend fun getTopRatedMovies(): Response<TopRatedMovieResponse>
    suspend fun getNowPlayingMovies(): Response<NowPlayingMovieResponse>
}