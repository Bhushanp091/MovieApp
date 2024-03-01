package com.example.movieapp.MovieList.presentation.viewModel

import com.example.jetmovieapp.movieLIist.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)