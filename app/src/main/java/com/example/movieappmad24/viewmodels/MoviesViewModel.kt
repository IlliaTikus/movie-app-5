package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MoviesViewModel (
    private val movieRepository: MovieRepository,
): ViewModel() {
    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    private val _favoriteMovies = MutableStateFlow(listOf<MovieWithImages>())
    val favoriteMovies: StateFlow<List<MovieWithImages>> = _favoriteMovies.asStateFlow()



    init {
        viewModelScope.launch {
            movieRepository.getAllMovies().distinctUntilChanged()
                .collect{ listOfMovies ->
                    _movies.value = listOfMovies
                }
        }
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().distinctUntilChanged()
                .collect{ listOfMovies ->
                    _favoriteMovies.value = listOfMovies
                }
        }

//        viewModelScope.launch {
//            movieWithImagesRepository.getById(movieId).distinctUntilChanged().collect {
//                    movWithImg ->
//                // maybe handle errors
//                if (movWithImg != null) {
//                    _movie.value = movWithImg
//                }
//            }
//        }
    }

//    suspend fun getById(movieId: String) {
//        val movieWithImages = movieWithImagesRepository.getById(movieId).firstOrNull()
//        _movie.value = movieWithImages
//    }

    fun toggleFavoriteMovie(movieId: Long) {
        viewModelScope.launch {
            val movie = _movies.value.find { it.movie.movieId == movieId }
            movie?.let {
                it.movie.isFavorite = !it.movie.isFavorite
                movieRepository.updateMovie(it.movie)
            }
        }
    }


}