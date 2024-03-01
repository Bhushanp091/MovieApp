package com.example.movieapp.MovieList.presentation.viewModel

sealed interface MovieListUiEvent {
    data class Paginate(val type:String):MovieListUiEvent
}