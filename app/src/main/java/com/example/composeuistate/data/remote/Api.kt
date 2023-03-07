package com.example.composeuistate.data.remote

import com.example.composeuistate.domain.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/top_rated")
    @WrapperMovieResults
    suspend fun getTopRated(): List<Movie>

    @GET("discover/movie")
    @WrapperMovieResults
    suspend fun getMoviesForGenre(@Query("with_genres") ids: String): List<Movie>
}