package com.example.composeuistate.domain.usecases

import com.example.composeuistate.domain.model.MovieGenre
import com.example.composeuistate.domain.repositories.MovieRepository

typealias RefreshGenreBaseUseCase = BaseUseCase<MovieGenre,Unit>

class RefreshGenreUseCase(private val repository: MovieRepository):RefreshGenreBaseUseCase{
    override suspend fun invoke(params: MovieGenre) {
        return repository.refreshGenre(params)
    }
}