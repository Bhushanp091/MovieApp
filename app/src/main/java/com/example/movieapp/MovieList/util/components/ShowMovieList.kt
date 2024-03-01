package com.example.movieapp.MovieList.util.components

import MovieItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieapp.MovieList.presentation.viewModel.MovieListState
import com.example.movieapp.MovieList.util.Constants
import com.example.movieapp.MovieList.util.Screen

@Composable
fun ShowMovieList(
    type: String,
    title:String,
    navController: NavController,
    bottomNavController: NavController,
    movieListState: MovieListState
) {

    val mediaList = when (type) {
        Constants.latest -> {
            movieListState.latestAll.take(20)
        }

        Constants.tvSeriesScreen -> {
            movieListState.tvSeriesAll.take(20)
        }

        Constants.popularScreen -> {
            movieListState.popularAll.take(20)
        }

        Constants.topRatedAllListScreen -> {
            movieListState.topRatedAll.take(20)
        }

        else -> {
            movieListState.topRatedMovieList.take(20)
        }
    }


    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier
                    .alpha(0.7f)
                    .clickable {
                       bottomNavController.navigate(Screen.MovieList.rout.replace("{type}",title))
                    },
                text = "See All",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 14.sp,
            )
        }

        LazyRow {
            items(mediaList.size) {

                var paddingEnd = 0.dp
                if (it == mediaList.size - 1) {
                    paddingEnd = 16.dp
                }

                MovieItem(
                    movie = mediaList[it],
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .padding(start = 16.dp, end = paddingEnd),
                    onMovieClick = {
                        navController.navigate(Screen.Details.rout.replace("{movieId}",it.toString()))
                    }
                )
            }
        }
    }
}