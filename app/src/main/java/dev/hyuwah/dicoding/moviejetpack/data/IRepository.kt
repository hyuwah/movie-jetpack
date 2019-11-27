package dev.hyuwah.dicoding.moviejetpack.data

import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem

interface IRepository {

    suspend fun fetchDiscoverMovies(): List<MovieItem>
    suspend fun fetchDiscoverTvShow(): List<MovieItem>

    suspend fun fetchMovieDetailById(id: Int): MovieItem
    suspend fun fetchTvShowDetailById(id: Int): MovieItem

}