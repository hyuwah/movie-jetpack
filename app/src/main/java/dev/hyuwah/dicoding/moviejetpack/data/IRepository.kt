package dev.hyuwah.dicoding.moviejetpack.data

import androidx.lifecycle.LiveData
import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem
import dev.hyuwah.dicoding.moviejetpack.presentation.model.MovieItem

interface IRepository {

    suspend fun fetchDiscoverMovies(): List<MovieItem>
    suspend fun fetchDiscoverTvShow(): List<MovieItem>

    suspend fun fetchMovieDetailById(id: Int): MovieItem
    suspend fun fetchTvShowDetailById(id: Int): MovieItem

    fun getFavoriteMovies(): LiveData<List<FavoriteItem>>
    fun getFavoriteTvShow(): LiveData<List<FavoriteItem>>
    fun getAllFavorite(): List<FavoriteItem>
    suspend fun getFavoriteById(id: Int): FavoriteItem?
    suspend fun addFavorite(favoriteItem: FavoriteItem)
    suspend fun removeFavorite(id: Int)

}