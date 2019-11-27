package dev.hyuwah.dicoding.moviejetpack.presentation.model

import android.os.Parcelable
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