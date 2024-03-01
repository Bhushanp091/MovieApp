package com.example.movieapp.MovieList.data.remote

import com.example.jetmovieapp.movieLIist.data.remote.respond.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface MovieListApi {
    //For getting movies and tv series from api
    @GET("{type}/{category}")
    suspend fun getMovieList(
        @Path("type") type: String,
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieListDto

    //When user Search for movies
    @GET("search/multi")
    suspend fun searchMovieList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apikey: String = API_KEY
    ):MovieListDto


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "b5ead859ece736dd045495a81e43a2d3"
    }
}