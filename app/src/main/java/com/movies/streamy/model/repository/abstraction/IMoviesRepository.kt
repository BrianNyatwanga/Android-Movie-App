package com.movies.streamy.model.repository.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResponse

interface IMoviesRepository {
    suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse>
    suspend fun getPopularMovies(): NetworkResponse<PopularMovieResponse, ErrorResponse>
    suspend fun getTopRatedMovies(): NetworkResponse<TopRatedMovieResponse, ErrorResponse>
    suspend fun getNowPlayingMovies(): NetworkResponse<NowPlayingMovieResponse, ErrorResponse>
    suspend fun getNowPlayingMovies2(): NetworkResponse<NowPlayingMovieResponse, ErrorResponse>
}