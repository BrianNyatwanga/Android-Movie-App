package com.movies.streamy.di

import com.movies.streamy.model.dataSource.abstraction.IMoviesDataSource
import com.movies.streamy.model.dataSource.abstraction.SeriesDataSource
import com.movies.streamy.model.dataSource.implementation.MoviesDataSourceImpl
import com.movies.streamy.model.dataSource.implementation.SeriesDataSourceImpl
import com.movies.streamy.model.dataSource.network.apiService.MoviesApiInterface
import com.movies.streamy.model.dataSource.network.apiService.SeriesApiInterface
import com.movies.streamy.model.repository.abstraction.IMoviesRepository
import com.movies.streamy.model.repository.abstraction.SeriesRepository
import com.movies.streamy.model.repository.implementation.MoviesRepositoryImpl
import com.movies.streamy.model.repository.implementation.SeriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    @Singleton
    fun provideMoviesApiService(
        retrofit: Retrofit
    ): MoviesApiInterface = retrofit.create(MoviesApiInterface::class.java)

    @Provides

    fun provideSeriesApiService
                (retrofit: Retrofit)
            : SeriesApiInterface = retrofit.create(SeriesApiInterface::class.java)

}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MovieBindingModule {

    @Binds
    abstract fun bindHomeDataSourceImpl(impl: MoviesDataSourceImpl): IMoviesDataSource

    @Binds
    abstract fun bindHomeRepositoryImpl(impl: MoviesRepositoryImpl): IMoviesRepository

    @Binds
    abstract fun bindSeriesDataSourceImpl(impl: SeriesDataSourceImpl): SeriesDataSource

    @Binds
    abstract fun bindSeriesRepositoryImpl(impl: SeriesRepositoryImpl): SeriesRepository
}