package dev.hyuwah.dicoding.moviejetpack.data

import androidx.annotation.DrawableRes

data class MovieModel(
    var id: Int,
    var title: String = "",
    var overview: String = "",
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0,
    @DrawableRes val posterPath: Int = -1,
    var releaseDate: String = "",
    var runtime: String = "",
    var genre: String = ""
) {
}