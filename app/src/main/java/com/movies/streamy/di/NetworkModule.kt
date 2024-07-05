package com.movies.streamy.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.movies.streamy.BuildConfig
import com.movies.streamy.model.dataSource.implementation.TrailerImpl
import com.movies.streamy.model.dataSource.network.apiService.TrailerInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun baseUrl() = BuildConfig.BASE_URL
    val token = ProvidesTokenInterceptor()

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }
    class ProvidesTokenInterceptor(): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val req = chain.request().newBuilder().addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}").build()
            return chain.proceed(req)
        }

    }

    @ProvideOkHttpClient
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(token)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val gson: Gson =
            GsonBuilder()
                .registerTypeAdapter(Date::class.java, GsonUTCDateAdapter())
                .setLenient()
                .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideRetrofitBuilder(
        @ProvideOkHttpClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideTrailerInterface(retrofit: Retrofit): TrailerInterface {
        return retrofit.create(TrailerInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideTrailerImpl(trailerInterface: TrailerInterface): TrailerImpl {
        return TrailerImpl(trailerInterface)
    }
}

class GsonUTCDateAdapter : JsonSerializer<Date?>, JsonDeserializer<Date?> {
    private val dateFormat: DateFormat

    @Synchronized
    override fun serialize(
        date: Date?,
        type: Type?,
        jsonSerializationContext: JsonSerializationContext?,
    ): JsonElement {
        return JsonPrimitive(dateFormat.format(date!!))
    }

    @Synchronized
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type?,
        jsonDeserializationContext: JsonDeserializationContext?,
    ): Date {
        return try {
            dateFormat.parse(jsonElement.asString)!!
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }
    }

    init {
        dateFormat =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            ) //This is the format I need
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }
}


