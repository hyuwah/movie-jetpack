package dev.hyuwah.dicoding.moviejetpack.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem

@Dao
interface FavoritesDao {

    @Insert
    suspend fun insert(movie: FavoriteItem)

    @Query("SELECT * FROM favorites WHERE category = \"movie\"")
    fun getFavoriteMovies(): LiveData<List<FavoriteItem>>

    @Query("SELECT * FROM favorites WHERE category = \"tvshow\"")
    fun getFavoriteTvShow(): LiveData<List<FavoriteItem>>

    @Query("SELECT * FROM favorites")
    fun getAllFavorite(): DataSource.Factory<Int, FavoriteItem>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getFavoriteById(id: Int): FavoriteItem?

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun delete(id: Int)

}