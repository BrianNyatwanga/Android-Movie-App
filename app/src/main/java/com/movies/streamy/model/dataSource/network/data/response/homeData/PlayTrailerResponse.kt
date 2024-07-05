package com.movies.streamy.model.dataSource.network.data.response.homeData

data class PlayTrailerResponse(
    val id: Int,
    val results: List<TrailerResult>
)