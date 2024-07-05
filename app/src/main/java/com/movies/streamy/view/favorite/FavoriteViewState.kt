package com.movies.streamy.view.favorite

sealed class FavoriteViewState {
    /**
     * Network state loading.
     */
    object Loading : FavoriteViewState ()

    /**
     * Network state Success.
     */
    object Success : FavoriteViewState ()

    /**
     * Error occurred state.
     */
    data class Error(
        val errorMessage: String?,
        val stringResourceId: Int?,
        val errorCode: Int?
    ) : FavoriteViewState ()

}