package com.example.movieapp.MovieList.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovie(movies:List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun  getAllMoviesById(id:Int):MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun  getAllMoviesByCategory(category:String):List<MovieEntity>


}