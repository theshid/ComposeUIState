package com.example.composeuistate.domain.usecases

import com.example.composeuistate.domain.model.Movie
import com.example.composeuistate.domain.model.MovieGenre
import com.example.composeuistate.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow


typealias GetMoviesBaseUseCase = BaseUseCaseWithoutSuspend<MovieGenre,Flow<List<Movie>>>

class GetMoviesUseCase(private val repository: MovieRepository):GetMoviesBaseUseCase{
    override fun invoke(params: MovieGenre): Flow<List<Movie>> {
        return repository.getMoviesStream(params)
    }

}