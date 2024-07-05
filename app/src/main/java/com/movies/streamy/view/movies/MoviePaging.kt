package com.movies.streamy.view.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movies.streamy.model.dataSource.network.apiService.MoviesApiInterface
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult

class MoviePaging(
    private val movieApiInterface: MoviesApiInterface
) : PagingSource<Int, NowPlayingMovieResult>() {

    override fun getRefreshKey(state: PagingState<Int, NowPlayingMovieResult>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingMovieResult> {
        val page = params.key ?: 1

        return try {
            val response = movieApiInterface.getNowPlayingMovies(page)

            if (response.isSuccessful) {
                val nowPlayingMovieResponse = response.body()
                val movies = nowPlayingMovieResponse?.results?.filterNotNull() ?: emptyList()

                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (movies.isNotEmpty()) page + 1 else null

                LoadResult.Page(
                    data = movies,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}
