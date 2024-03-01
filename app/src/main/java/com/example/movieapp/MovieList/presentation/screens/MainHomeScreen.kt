package com.example.movieapp.MovieList.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieapp.MovieList.util.components.ShowMediaHomeScreen
import com.example.movieapp.MovieList.presentation.viewModel.MovieListState
import com.example.movieapp.MovieList.util.Constants
import kotlin.reflect.KFunction2

@Composable
fun MainHomeScreen(
    navController: NavHostController,
    bottomNavHostController: NavHostController,
    movieListState: MovieListState,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 60.dp),
        ) {

            ShowMediaHomeScreen(
                type = Constants.popularScreen,
                title = "Popular",
                isLoading = movieListState.popularAll.isEmpty(),
                navController = navController,
                bottomNavController = bottomNavHostController,
                movieListState = movieListState
            )

            ShowMediaHomeScreen(
                type = Constants.latest,
                title = "Latest",
                isLoading = movieListState.latestAll.isEmpty(),
                navController = navController,
                movieListState = movieListState,
                bottomNavController = bottomNavHostController
            )

            ShowMediaHomeScreen(
                type = Constants.topRatedAllListScreen,
                title = "Top Rated",
                isLoading = movieListState.topRatedAll.isEmpty(),
                navController = navController,
                movieListState = movieListState,
                bottomNavController = bottomNavHostController
            )
            ShowMediaHomeScreen(
                type = Constants.tvSeriesScreen,
                title = "TV Series",
                isLoading = movieListState.topRatedAll.isEmpty(),
                navController = navController,
                movieListState = movieListState,
                bottomNavController = bottomNavHostController
            )

        }

    }

}