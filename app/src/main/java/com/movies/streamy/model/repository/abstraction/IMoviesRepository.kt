package com.movies.streamy.model.repository.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import retrofit2.Response

interface IMoviesRepository {
    suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse>
    suspend fun getPopularMovies(): Response<PopularMovieResponse>
}