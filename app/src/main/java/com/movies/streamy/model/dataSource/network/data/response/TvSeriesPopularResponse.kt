package com.movies.streamy.model.dataSource.network.data.response

import com.google.gson.annotations.SerializedName

data class TvSeriesPopularResponse(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<SeriesPopular>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
)
