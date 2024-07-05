package com.movies.streamy.view.series

sealed class SeriesViewState {
    object Loading : SeriesViewState()
    object Success : SeriesViewState()
    data class Error(
        val errorBody: String?,
        val message: Int?,
        val url: String?
    ) : SeriesViewState()
}
