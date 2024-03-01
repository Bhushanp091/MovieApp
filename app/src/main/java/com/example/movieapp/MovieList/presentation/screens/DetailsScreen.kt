package com.example.movieapp.MovieList.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetmovieapp.movieLIist.domain.model.Movie
import com.example.movieapp.MovieList.data.remote.MovieListApi
import com.example.movieapp.MovieList.presentation.viewModel.MovieListViewModel
import com.example.movieapp.MovieList.util.Constants
import com.example.movieapp.MovieList.util.RatingBarCompose
import com.example.movieapp.MovieList.util.components.ShowMediaHomeScreen

@Composable
fun DetailsScreen(movieId: String, navController: NavHostController) {

    val viewModel = hiltViewModel<MovieListViewModel>()

    LaunchedEffect(true) {
        viewModel.getMoviesById(movieId.toInt())
    }


    val detailsState = viewModel.detailsState.collectAsState().value
    val movieListState = viewModel.movieListState.collectAsState().value

    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieListApi.IMAGE_BASE_URL + detailsState.movie?.backdrop_path)
            .size(Size.ORIGINAL).build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieListApi.IMAGE_BASE_URL + detailsState.movie?.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state




    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {


        if (backDropImageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = detailsState.movie?.title
                )
            }
        }

        if (backDropImageState is AsyncImagePainter.State.Success) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(300.dp),
                    painter = backDropImageState.painter,
                    contentDescription = detailsState.movie?.title,
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(30),
                    color = Color.Black.copy(alpha = 0.3f),
                    contentColor = Color.White,
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .background(color = Color.Transparent),
                    contentAlignment = Alignment.BottomStart
                ) {
                    detailsState.movie?.let { movie ->
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .alpha(1f)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = movie.title.ifEmpty {
                                    movie.original_title
                                },
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            )

                        }

                    }
                }


            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
            ) {
                if (posterImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = detailsState.movie?.title
                        )
                    }
                }

                if (posterImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = posterImageState.painter,
                        contentDescription = detailsState.movie?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            detailsState.movie?.let { movie ->
                MovieInfoSection(movie = movie)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Description",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        detailsState.movie?.let {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = it.overview,
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        ShowMediaHomeScreen(
            type = Constants.popularScreen,
            title = "Recommended",
            isLoading = movieListState.popularMovieList.isEmpty(),
            navController = navController,
            movieListState = movieListState,
            bottomNavController = navController
        )

    }


}

@Composable
fun MovieInfoSection(movie: Movie) {
    Column(
        modifier = Modifier.padding(start = 32.dp)
    ) {

        Text(
            text = movie.title,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBarCompose(
                modifier = Modifier,
                starsModifier = Modifier.size(18.dp),
                rating = movie.vote_average.div(2)
            )

            Text(
                modifier = Modifier.padding(
                    horizontal = 4.dp
                ),
                text = movie.vote_average.toString().take(3),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(
                horizontal = 4.dp
            ),
            text = "Language: ${movie.original_language}",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.padding(
                horizontal = 4.dp
            ),
            text = movie.release_date.take(4),
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(6.dp))


    }
}