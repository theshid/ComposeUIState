package com.example.composeuistate.app.di

import com.example.composeuistate.data.repositoryImpl.MovieRepositoryImpl
import com.example.composeuistate.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsMoviesRepository(moviesRepository: MovieRepositoryImpl): MovieRepository
}