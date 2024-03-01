package com.example.movieapp.MovieList.domain.repository

import com.example.jetmovieapp.movieLIist.domain.model.Movie
import com.example.movieapp.MovieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        forceFetchFromRemote:Boolean,
        type:String,
        category:String,
        page:Int
    ):Flow<Resource<List<Movie>>>

    suspend fun getMovie(id:Int):Flow<Resource<Movie>>
}