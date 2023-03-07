package com.example.composeuistate.app.ui.ui_state

import androidx.compose.runtime.Immutable
import com.example.composeuistate.domain.model.Movie

data class HomeUiState(
    val topRatedMovies: TopRatedMoviesUiState,
    val actionMovies: ActionMoviesUiState,
    val animationMovies: AnimationMoviesUiState,
    val isRefreshing: Boolean,
    val isError: Boolean
)
@Immutable
sealed interface TopRatedMoviesUiState{
    data class Success(val movies: List<Movie>) : TopRatedMoviesUiState
    object Error : TopRatedMoviesUiState
    object Loading : TopRatedMoviesUiState
}

@Immutable
sealed interface AnimationMoviesUiState {
    data class Success(val movies: List<Movie>) : AnimationMoviesUiState
    object Error : AnimationMoviesUiState
    object Loading : AnimationMoviesUiState
}

@Immutable
sealed interface ActionMoviesUiState {
    data class Success(val movies: List<Movie>) : ActionMoviesUiState
    object Error : ActionMoviesUiState
    object Loading : ActionMoviesUiState
}

sealed interface GenreUiState {
    data class Success(val movies: List<Movie>) : GenreUiState
    object Error : GenreUiState
    object Loading : GenreUiState
}

data class GenreScreenUiState(
    val genreState: GenreUiState
)