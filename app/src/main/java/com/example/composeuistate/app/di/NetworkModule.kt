package com.example.composeuistate.app.di

import com.example.composeuistate.data.remote.Api
import com.example.composeuistate.data.remote.MoviesJsonConverter
import com.example.composeuistate.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    fun provideApi(): Api {

        val authQueryAppenderInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            val url = chain.request().url
            val urlBuilder = url.newBuilder()
            if (url.queryParameter("api_key") == null) {
                urlBuilder.addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            }
            chain.proceed(
                requestBuilder
                    .url(urlBuilder.build())
                    .build()
            )
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(authQueryAppenderInterceptor)
            .addInterceptor(loggingInterceptor)

        val moshi = Moshi.Builder()
            .add(MoviesJsonConverter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpBuilder.build())
            .build()
            .create(Api::class.java)
    }
}