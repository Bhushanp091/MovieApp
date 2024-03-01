package com.example.movieapp.MovieList.util

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object MainHome:Screen("mainHome")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    object TvShowList : Screen("tvShow")
    object Details : Screen("details/{movieId}")
    object MovieList :Screen("movieList/{type}")
}