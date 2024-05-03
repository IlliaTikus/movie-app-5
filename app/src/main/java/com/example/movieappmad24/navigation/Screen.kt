package com.example.movieappmad24.navigation

const val DETAIL_ARGUMENT_KEY = "movieId"
sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home")
    data object DetailScreen : Screen("detailscreen/{movieId}") {
        fun route(movieId: Long) = "detailscreen/$movieId"
    }
    data object WatchlistScreen : Screen("watchlist")
}