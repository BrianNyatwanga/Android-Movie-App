package com.movies.streamy.model.dataSource.network.data.response

import com.google.gson.annotations.SerializedName

data class TvSeriesId(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("id")
    val id: String?
)