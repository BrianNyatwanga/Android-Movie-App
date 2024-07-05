package com.movies.streamy.network


import com.movies.streamy.model.dataSource.implementation.authdata
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    suspend fun signup(@Body request: authdata.SignupRequest): authdata.SignupResponse
    abstract fun login(request: authdata.LoginRequest): authdata.LoginResponse?
    abstract fun getWatchlist(): authdata.WatchlistResponse?
}
