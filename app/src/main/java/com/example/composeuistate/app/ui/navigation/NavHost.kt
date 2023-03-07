package com.example.composeuistate.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composeuistate.app.ui.views.ActionMoviesScreen
import com.example.composeuistate.app.ui.views.AnimationMoviesScreen
import com.example.composeuistate.app.ui.views.HomeScreen

@Composable
fun ComposeNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = View.Home.route,
        modifier = modifier
    ) {
        composable(View.Home.route) {
            HomeScreen(navController, modifier)
        }
        composable(View.ActionMovies.route) {
            ActionMoviesScreen()
        }
        composable(View.AnimationMovies.route) {
            AnimationMoviesScreen()
        }
    }
}