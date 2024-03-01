package com.example.movieapp.MovieList.data.respository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.jetmovieapp.movieLIist.data.Mappers.toMovie
import com.example.jetmovieapp.movieLIist.data.Mappers.toMovieEntity
import com.example.jetmovieapp.movieLIist.domain.model.Movie
import com.example.movieapp.MovieList.data.local.MovieDatabase
import com.example.movieapp.MovieList.data.remote.MovieListApi
import com.example.movieapp.MovieList.domain.repository.MovieListRepository
import com.example.movieapp.MovieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieListApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)//https error thing
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        type:String,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            //getting movie from database

            val localMovieList = movieDatabase.movieDao.getAllMoviesByCategory(category)
            val loadLocalMovieLIst = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (loadLocalMovieLIst) {
                emit(Resource.Success(localMovieList.map { movieEntity -> //Mapping MovieEntity to Movie State Dc
                    movieEntity.toMovie(category)
                }))
                emit(Resource.Loading(false))
                return@flow
            }


            //getting movies from api if database is empty
            val moviesFromApi = try {
                movieApi.getMovieList(type, category, page)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error Loading Movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error Loading Movies"))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Error Loading Movies"))
                return@flow
            }

            //Storing data in database first them retrieving from database
            val moviesEntities = moviesFromApi.results.let {
                it.map { moviesDto ->
                    moviesDto.toMovieEntity(category)   //Mapping MovieDto to MovieEntity
                }
            }

            movieDatabase.movieDao.upsertMovie(moviesEntities)
            emit(Resource.Success(moviesEntities.map {
                it.toMovie(category)
            }))

            emit(Resource.Loading(false))

        }

    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
       return flow {
           emit(Resource.Loading(true))

           val movieEntity = movieDatabase.movieDao.getAllMoviesById(id)

           if (movieEntity != null) {
               emit(
                   Resource.Success(movieEntity.toMovie(movieEntity.category))
               )

               emit(Resource.Loading(false))
               return@flow
           }

           emit(Resource.Error("Error no such movie"))

           emit(Resource.Loading(false))
       }
    }
}