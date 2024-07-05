package com.movies.streamy.di

import com.movies.streamy.model.dataSource.abstraction.IHomeDataSource
import com.movies.streamy.model.dataSource.implementation.HomeDataSourceImpl
import com.movies.streamy.model.dataSource.network.apiService.HomeApiInterface
import com.movies.streamy.model.repository.abstraction.IHomeRepository
import com.movies.streamy.model.repository.implementation.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeModule {
    @Provides
    fun provideHomeApiService(
        retrofit: Retrofit
    ): HomeApiInterface = retrofit.create(HomeApiInterface::class.java)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HomeBindingModule {
    @Binds
    abstract fun bindHomeDataSourceImpl(impl: HomeDataSourceImpl): IHomeDataSource

    @Binds
    abstract fun bindHomeRepositoryImpl(impl: HomeRepositoryImpl): IHomeRepository

}