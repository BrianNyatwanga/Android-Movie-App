package com.movies.streamy.model.dataSource.network.apiService

import androidx.lifecycle.LiveData
import com.movies.streamy.model.dataSource.implementation.authdata
import com.movies.streamy.network.RetrofitClient
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import androidx.lifecycle.*
import com.movies.streamy.model.*
import kotlinx.coroutines.launch
interface auth {

    @POST("login")
    suspend fun login(@Body request: authdata.LoginRequest): authdata.LoginResponse



    class AuthViewModel : ViewModel() {
        private val _loginResult = MutableLiveData<authdata.LoginResponse?>()
        val loginResult: MutableLiveData<authdata.LoginResponse?> get() = _loginResult

        fun login(request: authdata.LoginRequest) {
            viewModelScope.launch {
                val response = RetrofitClient.apiService.login(request)
                _loginResult.postValue(response)
            }
        }
    }

    @POST("signup")
    suspend fun signup(@Body request: authdata.SignupRequest): authdata.SignupResponse

    @GET("watchlist")
    suspend fun getWatchlist(): authdata.WatchlistResponse
}