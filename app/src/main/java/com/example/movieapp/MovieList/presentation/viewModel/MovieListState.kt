package com.example.movieapp.MovieList.presentation.viewModel

import com.example.jetmovieapp.movieLIist.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val topRatedMovieListPage: Int = 1,
    val popularTvSeriesPage: Int = 1,
    val topRatedTvSeriesPage:Int = 1,

    val isCurrentPopularScreen: Boolean = true,

    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
    val topRatedMovieList: List<Movie> = emptyList(),
    val popularTvSeries: List<Movie> = emptyList(),
    val topRatedTvSeries: List<Movie> = emptyList(),


    val trendingNowAll:List<Movie> = emptyList(),
    val topRatedAll :List<Movie> = emptyList(),
    val latestAll:List<Movie> = emptyList(),
    val tvSeriesAll:List<Movie> = emptyList(),
    val popularAll:List<Movie> = emptyList(),
    val trendingNow:List<Movie> = emptyList(),
)
