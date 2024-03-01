package com.example.movieapp.MovieList.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetmovieapp.movieLIist.presentation.screens.components.MovieItem
import com.example.movieapp.MovieList.presentation.viewModel.MovieListState
import com.example.movieapp.MovieList.presentation.viewModel.MovieListUiEvent
import com.example.movieapp.MovieList.util.Category
import com.example.movieapp.MovieList.util.Constants


@Composable
fun TvShowList(
    movieListState: MovieListState,
    navController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {

    if (movieListState.tvSeriesAll.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(movieListState.tvSeriesAll.size) { index ->
                MovieItem(
                    movie = movieListState.tvSeriesAll[index],
                    navController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (index >= movieListState.tvSeriesAll.size - 1 && !movieListState.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Constants.tvSeriesScreen))
                }

            }
        }
    }

}