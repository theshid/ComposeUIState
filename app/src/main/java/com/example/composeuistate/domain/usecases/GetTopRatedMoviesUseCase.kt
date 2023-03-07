package com.example.composeuistate.domain.usecases

import com.example.composeuistate.domain.model.Movie
import com.example.composeuistate.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

typealias GetTopRatedMoviesBaseUseCase = BaseUseCaseWithoutSuspend<Unit, Flow<List<Movie>>>

class GetTopRatedMoviesUseCase(private val movieRepository: MovieRepository):GetTopRatedMoviesBaseUseCase{
    override fun invoke(params: Unit): Flow<List<Movie>> {
        return movieRepository.getTopRatedMoviesStream()
    }

}