package dev.hyuwah.dicoding.moviejetpack.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteItem(
    @ColumnInfo(name = "id")
    val id: Int,
    val title: String,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    @ColumnInfo(name = "backdrop_url")
    val backdropUrl: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val overview: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    val category: String
) {
    @PrimaryKey(autoGenerate = true)
    var fav_id: Int = 0
}