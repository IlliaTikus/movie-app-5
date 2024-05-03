package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(
    navController: NavController,
){
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val movieWithImagesDao = db.movieImageDao()
    val movieDao = db.movieDao()
    val movieRepository = MovieRepository(movieDao = movieDao, movieImageDao = movieWithImagesDao)
    val factory = MoviesViewModelFactory(movieRepository = movieRepository) // Pass MovieRepository to the factory
    val viewModel: MoviesViewModel = viewModel(factory = factory)

    val favoriteMoviesState by viewModel.favoriteMovies.collectAsState()

    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Your Watchlist")
        },
        bottomBar = {
            SimpleBottomAppBar(
                navController = navController
            )
        }
    ){ innerPadding ->

        MovieList(
            modifier = Modifier.padding(innerPadding),
            movies = favoriteMoviesState,
            navController = navController,
            viewModel = viewModel
        )

    }
}