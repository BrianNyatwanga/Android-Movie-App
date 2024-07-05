package com.movies.streamy.model.repository.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeTrendingResponse

interface IHomeRepository {
   suspend fun getMovieLists(): NetworkResponse<HomeTrendingResponse, ErrorResponse>

}