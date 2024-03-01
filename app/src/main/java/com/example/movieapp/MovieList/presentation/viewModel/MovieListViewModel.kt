package com.example.movieapp.MovieList.presentation.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.MovieList.domain.repository.MovieListRepository
import com.example.movieapp.MovieList.util.Category
import com.example.movieapp.MovieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movieapp.MovieList.presentation.viewModel.MovieListState
import com.example.movieapp.MovieList.util.Constants
import com.example.movieapp.MovieList.util.Type


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieListRepository
) : ViewModel() {

    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()
    private var _detialsState = MutableStateFlow(DetailsState())
    val detailsState = _detialsState.asStateFlow()


    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
        getTopRatedMovieList(false)
        getPopularTvSeries(false)
        getTopRatedTvSeries(false)
    }

    fun onEvent(event: MovieListUiEvent) {

        when (event) {
            is MovieListUiEvent.Paginate -> {
                when (event.type) {

                    Constants.homeScreen -> {
                        getPopularMovieList(true)
                        getUpcomingMovieList(true)
                        getTopRatedMovieList(true)
                        getPopularTvSeries(true)
                        getTopRatedTvSeries(true)
                    }

                    Constants.topRatedAllListScreen -> {
                        getTopRatedMovieList(true)
                        getTopRatedTvSeries(true)
                    }

                    Constants.latest -> {
                        getUpcomingMovieList(true)
                    }

                    Constants.popularScreen -> {
                        getPopularMovieList(true)
                        getPopularTvSeries(true)
                    }

                    Constants.tvSeriesScreen -> {
                        getTopRatedTvSeries(true)
                        getPopularTvSeries(true)
                    }


                }

            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            repository.getMovieList(
                forceFetchFromRemote,
                Type.Movie,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularMovie ->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList
                                            + popularMovie.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                            val shuffledMediaList = popularMovie.toMutableList()
                            shuffledMediaList.shuffle()
                            _movieListState.update {
                                it.copy(
                                    popularAll = movieListState.value.popularAll + shuffledMediaList.toList()
                                )

                            }
                        }

                    }
                }

            }
        }

    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            repository.getMovieList(
                forceFetchFromRemote,
                Type.Movie,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upcomingMovie ->
                            _movieListState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                            + upcomingMovie.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                            val shuffledMediaList = upcomingMovie.toMutableList()
                            shuffledMediaList.shuffle()
                            _movieListState.update {
                                it.copy(
                                    latestAll = movieListState.value.latestAll + shuffledMediaList.toList()
                                )

                            }
                        }
                    }
                }

            }
        }
    }

    private fun getTopRatedMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            repository.getMovieList(
                forceFetchFromRemote,
                Type.Movie,
                Category.TopRated,
                movieListState.value.topRatedMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { topRatedMovie ->
                            _movieListState.update {
                                it.copy(
                                    topRatedMovieList = movieListState.value.topRatedMovieList
                                            + topRatedMovie.shuffled(),
                                    topRatedMovieListPage = movieListState.value.topRatedMovieListPage + 1
                                )
                            }
                            val shuffledMediaList = topRatedMovie.toMutableList()
                            shuffledMediaList.shuffle()
                            _movieListState.update {
                                it.copy(
                                    topRatedAll = movieListState.value.topRatedAll + shuffledMediaList.toList()
                                )

                            }


                        }
                    }
                }

            }
        }
    }

    private fun getPopularTvSeries(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            repository.getMovieList(
                forceFetchFromRemote,
                Type.TvSeries,
                Category.POPULAR,
                movieListState.value.popularTvSeriesPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularTvS ->
                            _movieListState.update {
                                it.copy(
                                    popularTvSeries = movieListState.value.popularTvSeries
                                            + popularTvS.shuffled(),
                                    popularTvSeriesPage = movieListState.value.popularTvSeriesPage + 1
                                )
                            }
                            val shuffledMediaList = popularTvS.toMutableList()
                            shuffledMediaList.shuffle()
                            _movieListState.update {
                                it.copy(
                                    popularAll = movieListState.value.popularAll + shuffledMediaList.toList(),
                                    tvSeriesAll = movieListState.value.tvSeriesAll + shuffledMediaList.toList()
                                )

                            }

                        }
                    }
                }

            }
        }
    }

    private fun getTopRatedTvSeries(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            repository.getMovieList(
                forceFetchFromRemote,
                Type.TvSeries,
                Category.TopRated,
                movieListState.value.topRatedTvSeriesPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { topRatedTv ->
                            _movieListState.update {
                                it.copy(
                                    topRatedTvSeries = movieListState.value.topRatedTvSeries
                                            + topRatedTv.shuffled(),
                                    topRatedTvSeriesPage = movieListState.value.topRatedTvSeriesPage + 1
                                )
                            }
                            val shuffledMediaList = topRatedTv.toMutableList()
                            shuffledMediaList.shuffle()
                            _movieListState.update {
                                it.copy(
                                    topRatedAll = movieListState.value.topRatedAll + shuffledMediaList.toList(),
                                    tvSeriesAll = movieListState.value.tvSeriesAll + shuffledMediaList.toList()
                                )

                            }
                        }
                    }
                }

            }
        }
    }


    fun getMoviesById(id: Int) {
        viewModelScope.launch {
            _detialsState.update {
                it.copy(isLoading = true)
            }
            repository.getMovie(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _detialsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _detialsState.update {
                            it.copy(isLoading = detailsState.value.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { movie ->
                            _detialsState.update {
                                it.copy(
                                    movie = movie
                                )
                            }
                        }
                    }
                }

            }
        }
    }


}