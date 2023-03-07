package com.example.composeuistate.domain.usecases

import com.example.composeuistate.domain.repositories.MovieRepository

typealias RefreshTopRatedBaseUseCase = BaseUseCase<Unit,Unit>

class RefreshTopRatedUseCase(private val repository: MovieRepository):RefreshTopRatedBaseUseCase{
    override suspend fun invoke(params: Unit) {
        return repository.refreshTopRated()
    }
}