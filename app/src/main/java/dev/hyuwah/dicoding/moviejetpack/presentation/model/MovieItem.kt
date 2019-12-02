package dev.hyuwah.dicoding.moviejetpack.presentation.model

import android.os.Parcelable
import dev.hyuwah.dicoding.moviejetpack.data.local.entity.FavoriteItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(
    var id: Int = -1,
    var title: String = "",
    var posterUrl: String = "",
    var backdropUrl: String = "",
    var releaseDate: String = "",
    var overview: String = "",
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0
) : Parcelable

fun MovieItem.toFavoriteItem(category: String): FavoriteItem {
    return FavoriteItem(
        this.id,
        this.title,
        this.posterUrl,
        this.backdropUrl,
        this.releaseDate,
        this.overview,
        this.voteAverage,
        this.voteCount,
        category
    )
}