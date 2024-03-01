package com.example.movieapp.MovieList.di

import android.app.Application
import androidx.room.Room
import com.example.movieapp.MovieList.data.local.MovieDatabase
import com.example.movieapp.MovieList.data.remote.MovieListApi
import com.example.movieapp.MovieList.data.respository.MovieListRepositoryImpl
import com.example.movieapp.MovieList.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMovieApi() : MovieListApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieListApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieListApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "moviedb.db"
        ).build()
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository

}