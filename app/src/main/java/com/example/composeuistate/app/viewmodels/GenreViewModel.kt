package com.example.composeuistate.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeuistate.app.ui.ui_state.GenreScreenUiState
import com.example.composeuistate.app.ui.ui_state.GenreUiState
import com.example.composeuistate.app.utils.asResult
import com.example.composeuistate.domain.model.MovieGenre
import com.example.composeuistate.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(GenreScreenUiState(GenreUiState.Loading))
    val uiState = _uiState.asStateFlow()

    fun fetchMovies(genre: MovieGenre) {
        viewModelScope.launch {
            getMoviesUseCase(genre).asResult()
                .collect { result ->
                    val genreUiState = when (result) {
                        is com.example.composeuistate.app.utils.Result.Success -> GenreUiState.Success(
                            result.data
                        )
                        is com.example.composeuistate.app.utils.Result.Loading -> GenreUiState.Loading
                        is com.example.composeuistate.app.utils.Result.Error -> GenreUiState.Error
                    }

                    _uiState.value = GenreScreenUiState(genreUiState)
                }
        }
    }

}