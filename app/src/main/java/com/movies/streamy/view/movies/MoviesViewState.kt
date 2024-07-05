package com.movies.streamy.view.movies

sealed class MoviesViewState {

    /**
     * Network state loading.
     */
    object Loading : MoviesViewState()

    /**
     * Network state Success.
     */

    object Success : MoviesViewState()


    /**
     * Error occurred state.
     */

    data class Error(
        val errorMessage: String?,
        val stringResourceId: Int?,
        val errorCode: Int?
    ) : MoviesViewState()

}