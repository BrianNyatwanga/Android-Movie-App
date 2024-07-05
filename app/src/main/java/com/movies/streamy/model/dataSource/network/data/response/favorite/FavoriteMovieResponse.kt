package com.movies.streamy.model.dataSource.network.data.response.favorite

data class FavoriteMovieResponse(
    val page: Int,
    val results: List<Any>,
    val total_pages: Int,
    val total_results: Int
)