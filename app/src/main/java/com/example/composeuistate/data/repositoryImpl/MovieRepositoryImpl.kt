package com.example.composeuistate.data.repositoryImpl

import com.example.composeuistate.data.local.db.MovieDao
import com.example.composeuistate.data.local.model.MovieEntity
import com.example.composeuistate.data.local.model.asExternalModel
import com.example.composeuistate.data.remote.Api
import com.example.composeuistate.domain.model.Movie
import com.example.composeuistate.domain.model.MovieGenre
import com.example.composeuistate.domain.model.asEntity
import com.example.composeuistate.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
    private val api: Api
) : MovieRepository {

    override fun getTopRatedMoviesStream(): Flow<List<Movie>> {
        return dao.getTopRatedMoviesStream().map { entityMovies ->
            entityMovies.map(MovieEntity::asExternalModel)
        }.onEach {
            if (it.isEmpty()) {
                refreshTopRated()
            }
        }
    }

    override fun getMoviesStream(genre: MovieGenre): Flow<List<Movie>> {
        return dao.getGenreMoviesStream(genre.id).map { entityMovies ->
            entityMovies.map(MovieEntity::asExternalModel)
        }.onEach {
            if (it.isEmpty()) {
                refreshGenre(genre)
            }
        }
    }

    override suspend fun refreshTopRated() {
        api.getTopRated()
            .shuffled()
            .also { externalMovies ->
                dao.deleteAndInsert(movies = externalMovies.map(Movie::asEntity))
            }
    }

    override suspend fun refreshGenre(genre: MovieGenre) {
        api.getMoviesForGenre(genre.id)
            .shuffled()
            .also { externalMovies ->
                dao.deleteAndInsert(
                    genre.id, externalMovies.map { it.asEntity(genreId = genre.id) }
                )
            }
    }
}