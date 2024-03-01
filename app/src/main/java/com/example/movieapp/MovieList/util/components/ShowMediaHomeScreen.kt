package com.example.movieapp.MovieList.util.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.movieapp.MovieList.presentation.viewModel.MovieListState

@Composable
fun ShowMediaHomeScreen(
    type: String,
    title: String,
    isLoading: Boolean,
    navController: NavController,
    movieListState: MovieListState,
    bottomNavController: NavHostController
){

    if (isLoading){
        CircularProgressIndicator()
    }else{
        ShowMovieList(
            type = type,
            title = title,
            navController = navController,
            movieListState = movieListState,
            bottomNavController = bottomNavController
        )
    }


}