package com.movies.streamy.model.dataSource.network.data.response.homeData

import com.google.gson.annotations.SerializedName
data class HomeTrendingResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<HomeResult>?,
    @SerializedName("total_pages")
    val total_pages: Int?,
    @SerializedName("total_results")
    val total_results: Int?
)