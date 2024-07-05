package com.movies.streamy.model.dataSource.implementation

interface authdata {
    data class LoginRequest(val email: String, val password: String)
    data class LoginResponse(val success: Boolean, val message: String)

    data class SignupRequest(val email: String, val password: String)
    data class SignupResponse(val success: Boolean, val message: String)

    data class WatchlistItem(val id: Int, val title: String, val genre: String)
    data class WatchlistResponse(val items: List<WatchlistItem>)
}