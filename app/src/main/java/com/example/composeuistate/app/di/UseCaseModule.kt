package com.example.composeuistate.app.di

import com.example.composeuistate.domain.repositories.MovieRepository
import com.example.composeuistate.domain.usecases.GetMoviesUseCase
import com.example.composeuistate.domain.usecases.GetTopRatedMoviesUseCase
import com.example.composeuistate.domain.usecases.RefreshGenreUseCase
import com.example.composeuistate.domain.usecases.RefreshTopRatedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(movieRepository: MovieRepository): GetMoviesUseCase =
        GetMoviesUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetTopRatedMoviesUseCase(movieRepository: MovieRepository): GetTopRatedMoviesUseCase =
        GetTopRatedMoviesUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideRefreshGenreUseCase(movieRepository: MovieRepository): RefreshGenreUseCase =
        RefreshGenreUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideRefreshTopRatedUseCase(movieRepository: MovieRepository): RefreshTopRatedUseCase =
        RefreshTopRatedUseCase(movieRepository)
}