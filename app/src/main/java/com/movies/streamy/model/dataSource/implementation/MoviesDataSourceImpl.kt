package com.movies.streamy.model.dataSource.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.IMoviesDataSource
import com.movies.streamy.model.dataSource.network.apiService.MoviesApiInterface
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResponse
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(
    private val moviesApiInterface: MoviesApiInterface,
) : IMoviesDataSource {

    override suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse> {
        return moviesApiInterface.getMovieId()
    }

    override suspend fun getPopularMovies(): NetworkResponse<PopularMovieResponse, ErrorResponse> {
        return moviesApiInterface.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): NetworkResponse<TopRatedMovieResponse, ErrorResponse> {
        return moviesApiInterface.getTopRatedMovies()
    }

    override suspend fun getNowPlayingMovies(): NetworkResponse<NowPlayingMovieResponse, ErrorResponse> {
        return moviesApiInterface.getNowPlayingMovies(1)

    }

    override suspend fun getNowPlayingMovies2(): NetworkResponse<NowPlayingMovieResponse, ErrorResponse> {
        return moviesApiInterface.getNowPlayingMovies(2)

    }

}