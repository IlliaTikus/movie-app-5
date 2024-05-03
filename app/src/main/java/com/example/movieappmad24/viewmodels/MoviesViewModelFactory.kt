package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.data.MovieRepository

class MoviesViewModelFactory (private val movieRepository: MovieRepository): ViewModelProvider.Factory {
        override fun<T: ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MoviesViewModel::class.java)){
                return MoviesViewModel(movieRepository = movieRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


class DetailsViewModelFactory(private val movieRepository: MovieRepository,
                              private val movieId: Long): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(
                movieRepository = movieRepository,
                movieId = movieId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}