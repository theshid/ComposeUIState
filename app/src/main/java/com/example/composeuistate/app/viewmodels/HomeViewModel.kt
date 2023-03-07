package com.example.composeuistate.app.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.composeuistate.app.ui.ui_state.ActionMoviesUiState
import com.example.composeuistate.app.ui.ui_state.AnimationMoviesUiState
import com.example.composeuistate.app.ui.ui_state.HomeUiState
import com.example.composeuistate.app.ui.ui_state.TopRatedMoviesUiState
import com.example.composeuistate.app.utils.WhileUiSubscribed
import com.example.composeuistate.app.utils.asResult
import com.example.composeuistate.domain.model.Movie
import com.example.composeuistate.domain.model.MovieGenre
import com.example.composeuistate.domain.usecases.GetMoviesUseCase
import com.example.composeuistate.domain.usecases.GetTopRatedMoviesUseCase
import com.example.composeuistate.domain.usecases.RefreshGenreUseCase
import com.example.composeuistate.domain.usecases.RefreshTopRatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val refreshGenreUseCase: RefreshGenreUseCase,
    private val refreshTopRatedUseCase: RefreshTopRatedUseCase
) : BaseViewModel() {
    private val topRatedMovies: Flow<com.example.composeuistate.app.utils.Result<List<Movie>>> =
        getTopRatedMoviesUseCase(Unit).asResult()
    private val actionMovies: Flow<com.example.composeuistate.app.utils.Result<List<Movie>>> =
        getMoviesUseCase(MovieGenre.ACTION).asResult()
    private val animationMovies: Flow<com.example.composeuistate.app.utils.Result<List<Movie>>> =
        getMoviesUseCase(MovieGenre.ANIMATION).asResult()
    private val isRefreshing = MutableStateFlow(false)

    private val isError = MutableStateFlow(false)

    override val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                isError.emit(true)
            }
            Timber.e("error:${exception.message}")
        }

    val uiState: StateFlow<HomeUiState> = combine(
        topRatedMovies,
        actionMovies,
        animationMovies,
        isRefreshing,
        isError
    ) { topRatedResult, actionMoviesResult, animationMoviesResult, refreshing, errorOccurred ->

        val topRated: TopRatedMoviesUiState = when (topRatedResult) {
            is com.example.composeuistate.app.utils.Result.Success -> TopRatedMoviesUiState.Success(topRatedResult.data)
            is com.example.composeuistate.app.utils.Result.Loading -> TopRatedMoviesUiState.Loading
            is com.example.composeuistate.app.utils.Result.Error -> TopRatedMoviesUiState.Error
        }

        val action: ActionMoviesUiState = when (actionMoviesResult) {
            is com.example.composeuistate.app.utils.Result.Success -> ActionMoviesUiState.Success(actionMoviesResult.data)
            is com.example.composeuistate.app.utils.Result.Loading -> ActionMoviesUiState.Loading
            is com.example.composeuistate.app.utils.Result.Error -> ActionMoviesUiState.Error
        }

        val animation: AnimationMoviesUiState = when (animationMoviesResult) {
            is com.example.composeuistate.app.utils.Result.Success -> AnimationMoviesUiState.Success(animationMoviesResult.data)
            is com.example.composeuistate.app.utils.Result.Loading -> AnimationMoviesUiState.Loading
            is com.example.composeuistate.app.utils.Result.Error -> AnimationMoviesUiState.Error
        }

        HomeUiState(
            topRated,
            action,
            animation,
            refreshing,
            errorOccurred
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = HomeUiState(
                TopRatedMoviesUiState.Loading,
                ActionMoviesUiState.Loading,
                AnimationMoviesUiState.Loading,
                isRefreshing = false,
                isError = false
            )
        )

    fun onRefresh() {
        launchCoroutine {

                val refreshTopRatedDeferred = async { refreshTopRatedUseCase(Unit) }
                val refreshActionMoviesDeferred = async { refreshGenreUseCase(MovieGenre.ACTION) }
                val refreshAnimationMoviesDeferred = async { refreshGenreUseCase(MovieGenre.ANIMATION) }
                isRefreshing.emit(true)
                try {
                    awaitAll(
                        refreshTopRatedDeferred,
                        refreshActionMoviesDeferred,
                        refreshAnimationMoviesDeferred
                    )
                } finally {
                    isRefreshing.emit(false)
                }

        }
    }

    fun onErrorConsumed() {
        viewModelScope.launch {
            isError.emit(false)
        }
    }
}