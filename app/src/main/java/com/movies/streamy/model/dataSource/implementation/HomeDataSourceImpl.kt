package com.movies.streamy.model.dataSource.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.IHomeDataSource
import com.movies.streamy.model.dataSource.network.apiService.HomeApiInterface
import com.movies.streamy.model.dataSource.network.apiService.TrailerInterface
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.PlayTrailerResponse
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeApiInterface: HomeApiInterface
) : IHomeDataSource {

    override suspend fun getMovieLists(): NetworkResponse<HomeTrendingResponse, ErrorResponse> {
        return homeApiInterface.getMovieList()
    }
}

class TrailerImpl(private val trailerInterface: TrailerInterface) {
    suspend fun getTrailerByMovieId(movieId: Int): PlayTrailerResponse? {
        return when (val response = trailerInterface.playTrailer(movieId)) {
            is NetworkResponse.Success -> response.body
            else -> null
        }
    }
}

