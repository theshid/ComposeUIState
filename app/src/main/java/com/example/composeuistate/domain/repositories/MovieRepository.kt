package com.example.composeuistate.domain.repositories

import com.example.composeuistate.domain.model.Movie
import com.example.composeuistate.domain.model.MovieGenre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTopRatedMoviesStream(): Flow<List<Movie>>
    fun getMoviesStream(genre: MovieGenre): Flow<List<Movie>>
    suspend fun refreshTopRated()
    suspend fun refreshGenre(genre: MovieGenre)
}