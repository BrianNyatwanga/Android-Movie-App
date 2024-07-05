package com.movies.streamy.model.repository.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.IHomeDataSource
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeTrendingResponse
import com.movies.streamy.model.repository.abstraction.IHomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: IHomeDataSource,
) : IHomeRepository {
    override suspend fun getMovieLists(): NetworkResponse<HomeTrendingResponse, ErrorResponse> {
        return homeDataSource.getMovieLists()
    }
}