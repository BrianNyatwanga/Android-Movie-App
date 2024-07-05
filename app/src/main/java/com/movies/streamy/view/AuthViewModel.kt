package com.movies.streamy.viewmodel

import androidx.lifecycle.*
import com.movies.streamy.model.*
import com.movies.streamy.model.dataSource.implementation.authdata
import com.movies.streamy.network.RetrofitClient
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    val signupResult: Any
        get() {
            TODO()
        }
    private val _loginResult = MutableLiveData<authdata.LoginResponse?>()
    val loginResult: MutableLiveData<authdata.LoginResponse?> get() = _loginResult

    fun login(request: authdata.LoginRequest) {
        viewModelScope.launch {
            val response = RetrofitClient.apiService.login(request)
            _loginResult.postValue(response)
        }
    }
}
