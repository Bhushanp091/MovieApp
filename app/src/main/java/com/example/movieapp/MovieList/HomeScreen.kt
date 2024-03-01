package com.example.movieapp.MovieList

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material.icons.rounded.Whatshot
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetmovieapp.movieLIist.presentation.screens.PopularMoviesScreen
import com.example.jetmovieapp.movieLIist.presentation.screens.UpcomingMoviesScreen
import com.example.movieapp.MovieList.presentation.screens.MainHomeScreen
import com.example.movieapp.MovieList.presentation.screens.MediaTypeScreen
import com.example.movieapp.MovieList.presentation.screens.TvShowList
import com.example.movieapp.MovieList.presentation.viewModel.MovieListUiEvent
import com.example.movieapp.MovieList.presentation.viewModel.MovieListViewModel
import com.example.movieapp.MovieList.util.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieListState = movieListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
        com.example.movieapp.MovieList.BottomNavigationBar(
            bottomNavController = bottomNavController, onEvent = movieListViewModel::onEvent
        )
    }, topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Movie",
                    fontSize = 20.sp
                )
            },
            modifier = Modifier.shadow(2.dp),
            colors = TopAppBarDefaults.smallTopAppBarColors(
                MaterialTheme.colorScheme.inverseOnSurface
            )
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.MainHome.rout
            ) {
                composable(Screen.MainHome.rout) {
                    MainHomeScreen(
                        navController = navController,
                        movieListState = movieListState,
                        bottomNavHostController = bottomNavController
                    )
                }
                composable(Screen.PopularMovieList.rout) {
                    PopularMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovieList.rout) {
                    UpcomingMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.TvShowList.rout) {
                    TvShowList(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.MovieList.rout) { backstackEntry ->
                    val type = backstackEntry.arguments?.getString("type") ?: ""
                    MediaTypeScreen(
                        type = type,
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
            }
        }
    }

}


@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController, onEvent: (MovieListUiEvent) -> Unit
) {

    val items = listOf(
        com.example.movieapp.MovieList.BottomItem(
            title = "Home",
            icon = Icons.Rounded.Home
        ),
        com.example.movieapp.MovieList.BottomItem(
            title = "Popular",
            icon = Icons.Rounded.Whatshot
        ), com.example.movieapp.MovieList.BottomItem(
            title = "Latest",
            icon = Icons.Rounded.Upcoming
        ), com.example.movieapp.MovieList.BottomItem(
            title = "Tv Show",
            icon = Icons.Rounded.Tv
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue == index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {
                        0 -> {
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.MainHome.rout)
                        }

                        1 -> {
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PopularMovieList.rout)
                        }

                        2 -> {
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.UpcomingMovieList.rout)
                        }

                        3 -> {
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.TvShowList.rout)
                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }

}

data class BottomItem(
    val title: String, val icon: ImageVector
)