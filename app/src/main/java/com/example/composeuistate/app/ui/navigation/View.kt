package com.example.composeuistate.app.ui.navigation

sealed class View(val route: String) {
    object Home : View("home")
    object ActionMovies : View("action_movies_screen")
    object AnimationMovies : View("animation_movies_screen")
}
