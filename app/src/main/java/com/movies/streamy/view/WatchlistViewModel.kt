package com.movies.streamy.view
import androidx.lifecycle.*
import com.movies.streamy.model.dataSource.implementation.authdata
import com.movies.streamy.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope

class WatchlistViewModel {
    private val _watchlist = MutableLiveData<authdata.WatchlistResponse?>()
    val watchlist: MutableLiveData<authdata.WatchlistResponse?> get() = _watchlist

    fun fetchWatchlist(viewModelScope: Any) {
        viewModelScope.launch {
            val response = RetrofitClient.apiService.getWatchlist()
            _watchlist.postValue(response)
        }
    }

}

private fun Any.launch(block: suspend CoroutineScope.() -> Unit) {
    TODO("Not yet implemented")
}
