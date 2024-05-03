package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel

abstract class MoviesViewModel2 : ViewModel() {
    abstract suspend fun toggleFavoriteMovie(movieId: String)
}
